package com.example.demo.domain.service.like_post;

import com.example.demo.domain.entity.Account;
import com.example.demo.domain.entity.Post;
import com.example.demo.domain.model.LikePostRequest;
import com.example.demo.domain.model.LikePostResponse;
import com.example.demo.domain.service.crud.AccountService;
import com.example.demo.domain.service.crud.PostService;
import com.example.demo.exception.MissingParameterException;
import com.example.demo.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("likePostService")
public class LikePostService {

    @Autowired
    private PostService postService;

    @Autowired
    private AccountService accountService;

    public LikePostResponse like(Long accountId, LikePostRequest likePostRequest) throws MissingParameterException, NotFoundException {
        Account account = this.accountService.findByIdWithLikedPostsLoadedEagerly(accountId).get();

        Long id = likePostRequest.getId();

        if(id == null) throw new MissingParameterException("Parameter is not enough.");

        Optional<Post> postRs = this.postService.findByIdWithAllRelationshipsLoadedEagerly(id);

        if(postRs.isEmpty()) throw new NotFoundException("Post is not existed.");

        Post post = postRs.get();

        post.setLove(post.getLove() + 1);
        post.getSupporters().add(account);

        post = this.postService.saveOrUpdate(post);

        account.getLikedPosts().add(post);

        this.accountService.saveOrUpdate(account);

        return LikePostResponse.builder()
                .like(post.getLove())
                .build();
    }

}
