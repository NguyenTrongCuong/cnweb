package com.example.demo.domain.service.check_new_item;

import com.example.demo.domain.entity.*;
import com.example.demo.domain.model.*;
import com.example.demo.domain.service.crud.AccountService;
import com.example.demo.domain.service.crud.PostService;
import com.example.demo.exception.MissingParameterException;
import org.hibernate.annotations.Check;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service("checkNewItemService")
public class CheckNewItemService {

    @Autowired
    private PostService postService;

    @Autowired
    private AccountService accountService;

    public CheckNewItemResponse check(Long accountId, CheckNewItemRequest request) throws MissingParameterException {
        Long lastId = request.getLast_id();

        if(lastId == null) throw new MissingParameterException("Parameter is not enough.");

        if(lastId <= 0) throw new IllegalArgumentException("Parameter value is invalid.");

        Account account = this.accountService.findById(accountId).get();

        List<Post> posts = this.postService.findByAccountIdWithAllRelationshipsLoadedEagerlyV2(accountId);

        posts = posts.stream().filter(post -> post.getId() > lastId).collect(Collectors.toList());

        posts.sort(Comparator.comparing(Base::getId).reversed());

        return CheckNewItemResponse.builder()
                .new_items(String.valueOf(posts.size()))
                .build();
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
                .id(account.getId().toString())
                .name(account.getProfile().getUsername())
                .avatar(account.getProfile().getAvatarLink())
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
