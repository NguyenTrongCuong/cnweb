package com.example.demo.domain.service.get_list_posts;

import com.example.demo.domain.entity.Account;
import com.example.demo.domain.entity.Comment;
import com.example.demo.domain.entity.Post;
import com.example.demo.domain.entity.StaticResource;
import com.example.demo.domain.model.CommentInfo;
import com.example.demo.domain.model.GetListPostsRequest;
import com.example.demo.domain.model.GetListPostsResponse;
import com.example.demo.domain.model.Poster;
import com.example.demo.domain.service.crud.AccountService;
import com.example.demo.domain.service.crud.PostService;
import com.example.demo.exception.MissingParameterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service("getListPostsService")
public class GetListPostsService {

    @Autowired
    private PostService postService;

    @Autowired
    private AccountService accountService;

    public List<GetListPostsResponse> get(Long accountId, GetListPostsRequest request) throws MissingParameterException {
        Long id = request.getId();
        Integer index = request.getIndex();
        Integer count = request.getCount();
        Long lastId = request.getLast_id();
        Account account = this.accountService.findById(accountId).get();

        if(id == null ||
           index == null ||
           count == null ||
           lastId == null) throw new MissingParameterException("Parameter is not enough.");

        if(index <= 0 || count <= 0 || lastId <= 0) throw new IllegalArgumentException("Parameter value is invalid.");

        Pageable pageable = PageRequest.of(index - 1, count);

        List<Post> posts = this.postService.findByAccountIdWithAllRelationshipsLoadedEagerly(id, pageable);

        return posts.stream()
                .map(post -> GetListPostsResponse.builder()
                        .id(post.getId())
                        .description(post.getDescription())
                        .status(post.getStatus())
                        .like(post.getLove())
                        .images(getImagesAsList(post.getStaticResources()))
                        .video(getVideo(post.getStaticResources()))
                        .is_liked(isLiked(post.getSupporters(), account))
                        .author(accountToPoster(post.getAccount()))
                        .comments(commentsToCommentInfos(post.getComments()))
                        .build()
                )
                .collect(Collectors.toList());
    }

    public List<GetListPostsResponse> getV2(Long accountId, GetListPostsRequest request) throws MissingParameterException {
        Long id = request.getId();
        Integer index = request.getIndex();
        Integer count = request.getCount();
        Long lastId = request.getLast_id();
        Account account = this.accountService.findById(accountId).get();

        if(id == null ||
           index == null ||
           count == null ||
           lastId == null) throw new MissingParameterException("Parameter is not enough.");

        if(index <= 0 || count <= 0 || lastId <= 0) throw new IllegalArgumentException("Parameter value is invalid.");

        List<Post> posts = this.postService.findByIdAfter(id);

        posts = posts.subList(0, count);

        return posts.stream()
                .map(post -> GetListPostsResponse.builder()
                        .id(post.getId())
                        .description(post.getDescription())
                        .status(post.getStatus())
                        .like(post.getLove())
                        .images(getImagesAsList(post.getStaticResources()))
                        .video(getVideo(post.getStaticResources()))
                        .is_liked(isLiked(post.getSupporters(), account))
                        .author(accountToPoster(post.getAccount()))
                        .comments(commentsToCommentInfos(post.getComments()))
                        .build()
                )
                .collect(Collectors.toList());
    }

    private List<String> getImagesAsList(Set<StaticResource> staticResources) {
        return staticResources.stream()
                .filter(staticResource -> staticResource.getType().equalsIgnoreCase("IMAGE"))
                .map(staticResource -> staticResource.getLink())
                .collect(Collectors.toList());
    }

    private String getVideo(Set<StaticResource> staticResources) {
        Optional<StaticResource> rs = staticResources.stream()
                .filter(staticResource -> staticResource.getType().equalsIgnoreCase("VIDEO"))
                .findAny();

        return rs.isEmpty() ? "" : rs.get().getLink();
    }

    private boolean isLiked(Set<Account> accounts, Account account) {
        return accounts.contains(account);
    }
    
    private Poster accountToPoster(Account account) {
        return Poster.builder()
                .id(account.getId())
                .name(account.getProfile().getUsername())
                .avatar(account.getProfile().getAvatarLink())
                .build();
    }
    
    private List<CommentInfo> commentsToCommentInfos(Set<Comment> comments) {
        return comments.stream()
                .map(comment -> {
                    return CommentInfo.builder()
                            .id(comment.getId())
                            .created_at(comment.getCreatedAt().format(DateTimeFormatter.ISO_DATE_TIME))
                            .content(comment.getContent())
                            .build();
                })
                .collect(Collectors.toList());
    }

}
