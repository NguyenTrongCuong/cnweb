package com.example.demo.domain.service.get_list_posts;

import com.example.demo.domain.entity.*;
import com.example.demo.domain.model.*;
import com.example.demo.domain.service.check_new_item.CheckNewItemService;
import com.example.demo.domain.service.crud.AccountService;
import com.example.demo.domain.service.crud.PostService;
import com.example.demo.exception.MissingParameterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service("getListPostsService")
public class GetListPostsService {

    @Autowired
    private PostService postService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private CheckNewItemService checkNewItemService;

    public GetListPostResponseV2 get(Long accountId, GetListPostsRequest request) throws MissingParameterException {
        Long id = request.getId();
        Integer index = request.getIndex();
        Integer count = request.getCount();
        Long lastId = request.getLast_id();
        Integer inCampaign = request.getIn_campaign();
        Long campaignId = request.getCampaign_id();
        String latitude= request.getLatitude();
        String longitude = request.getLongitude();
        Account account = this.accountService.findById(accountId).get();

        if(id == null ||
           index == null ||
           count == null ||
           lastId == null ||
           inCampaign == null ||
           campaignId == null ||
           latitude == null ||
           longitude == null) throw new MissingParameterException("Parameter is not enough.");

        if(index <= 0 || count <= 0 || lastId <= 0) throw new IllegalArgumentException("Parameter value is invalid.");

        if(inCampaign != 0 && inCampaign != 1) throw new IllegalArgumentException("Parameter value is invalid.");

        Pageable pageable = PageRequest.of(index - 1, count, Sort.by(Sort.Order.desc("id")));

        List<Post> posts = this.postService.findByAccountIdWithAllRelationshipsLoadedEagerly(id, pageable);

        return GetListPostResponseV2.builder()
                .posts(posts.stream()
                        .map(post -> GetListPostsResponse.builder()
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
                                .build()
                        )
                        .collect(Collectors.toList()))
                .new_items(
                        this.checkNewItemService.check(id, CheckNewItemRequest.builder().last_id(lastId).build())
                                                .getNew_items()
                )
                .last_id(lastId.toString())
                .in_campaign(String.valueOf(inCampaign))
                .campaign_id(String.valueOf(campaignId))
                .build();
    }

//    public List<GetListPostsResponse> getV2(Long accountId, GetListPostsRequest request) throws MissingParameterException {
//        Long id = request.getId();
//        Integer index = request.getIndex();
//        Integer count = request.getCount();
//        Long lastId = request.getLast_id();
//        Account account = this.accountService.findById(accountId).get();
//
//        if(id == null ||
//           index == null ||
//           count == null ||
//           lastId == null) throw new MissingParameterException("Parameter is not enough.");
//
//        if(index <= 0 || count <= 0 || lastId <= 0) throw new IllegalArgumentException("Parameter value is invalid.");
//
//        List<Post> posts = this.postService.findByAccountIdWithAllRelationshipsLoadedEagerlyV2(id);
//
//        posts = posts.stream().filter(post -> post.getId() > lastId).collect(Collectors.toList());
//
//        posts.sort(Comparator.comparing(Base::getId).reversed());
//
//        if(count < posts.size()) posts = posts.subList(0, count);
//
//        return posts.stream()
//                .map(post -> GetListPostsResponse.builder()
//                        .id(post.getId().toString())
//                        .description(post.getDescription())
//                        .status(post.getStatus())
//                        .like(post.getLove().toString())
//                        .images(getImagesAsList(post.getStaticResources()))
//                        .video(getVideo(post.getStaticResources()))
//                        .is_liked(isLiked(post.getSupporters(), account))
//                        .author(accountToPoster(post.getAccount()))
//                        .comments(commentsToCommentInfos(post.getComments()))
//                        .build()
//                )
//                .collect(Collectors.toList());
//    }

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
