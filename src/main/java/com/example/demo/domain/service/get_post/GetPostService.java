package com.example.demo.domain.service.get_post;

import com.example.demo.domain.entity.Account;
import com.example.demo.domain.entity.Comment;
import com.example.demo.domain.entity.Post;
import com.example.demo.domain.entity.StaticResource;
import com.example.demo.domain.model.*;
import com.example.demo.domain.service.crud.AccountService;
import com.example.demo.domain.service.crud.PostService;
import com.example.demo.exception.MissingParameterException;
import com.example.demo.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service("getPostService")
public class GetPostService {

    @Autowired
    private PostService postService;

    @Autowired
    private AccountService accountService;

    public GetListPostsResponse get(Long accountId, GetPostRequest request) throws MissingParameterException, NotFoundException {
        Long id = request.getId();

        if(id == null) throw new MissingParameterException("Parameter is not enough.");

        Optional<Post> rs = this.postService.findByIdWithAllRelationshipsLoadedEagerly(id);

        if(rs.isEmpty()) throw new NotFoundException("Post is not existed.");

        Post post = rs.get();

        Account account = this.accountService.findById(accountId).get();

        return GetListPostsResponse.builder()
                .id(post.getId().toString())
                .described(post.getDescribed())
                .state(post.getStatus())
                .like(post.getLove().toString())
                .images(getImagesAsList(post.getStaticResources()))
                .video(getVideo(post.getStaticResources()))
                .is_liked(isLiked(post.getSupporters(), account))
                .author(accountToPoster(post.getAccount()))
                .comments(String.valueOf(post.getComments().size()))
                .created(post.getCreated().toString())
                .modified(post.getModified() == null ? null : post.getModified().toString())
                .is_blocked("0")
                .can_edit("1")
                .banned("0")
                .can_comment("1")
                .url("")
                .messages(new ArrayList<>())
                .build();
    }

    private List<ImageResponse> getImagesAsList(Set<StaticResource> staticResources) {
        staticResources = staticResources.stream().sorted(Comparator.comparing(StaticResource::getPosition)).collect(Collectors.toCollection(LinkedHashSet::new));
        return staticResources.stream()
                .filter(staticResource -> staticResource.getType().equalsIgnoreCase("IMAGE"))
                .map(staticResource -> ImageResponse.builder()
                        .id(String.valueOf(staticResource.getId()))
                        .url(staticResource.getLink())
                        .build())
                .collect(Collectors.toList());
    }

    private String getVideo(Set<StaticResource> staticResources) {
        Optional<StaticResource> rs = staticResources.stream()
                .filter(staticResource -> staticResource.getType().equalsIgnoreCase("VIDEO"))
                .findAny();

        return rs.isEmpty() ? "" : rs.get().getLink();
    }

    private String isLiked(Set<Account> accounts, Account account) {
        return String.valueOf(accounts.contains(account) ? 1 : 0);
    }

    private Poster accountToPoster(Account account) {
        return Poster.builder()
                .id(account.getId().toString())
                .name(account.getProfile().getUsername())
                .avatar(account.getProfile().getAvatarLink())
                .online("online")
                .build();
    }

    private List<CommentInfo> commentsToCommentInfos(Set<Comment> comments) {
        return comments.stream()
                .map(comment -> {
                    return CommentInfo.builder()
                            .id(comment.getId().toString())
                            .created_at(comment.getCreatedAt().format(DateTimeFormatter.ISO_DATE_TIME))
                            .content(comment.getContent())
                            .build();
                })
                .collect(Collectors.toList());
    }

}
