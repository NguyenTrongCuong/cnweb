package com.example.demo.domain.service.create_comment;

import com.example.demo.domain.entity.Account;
import com.example.demo.domain.entity.Comment;
import com.example.demo.domain.entity.Post;
import com.example.demo.domain.model.CreateCommentRequest;
import com.example.demo.domain.model.CreateCommentResponse;
import com.example.demo.domain.model.Poster;
import com.example.demo.domain.service.crud.AccountService;
import com.example.demo.domain.service.crud.CommentService;
import com.example.demo.domain.service.crud.PostService;
import com.example.demo.exception.AccountNotFoundException;
import com.example.demo.exception.MissingParameterException;
import com.example.demo.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class CreateCommentService {

    @Autowired
    private CommentService commentService;

    @Autowired
    private PostService postService;

    @Autowired
    private AccountService accountService;

    private static final int MAX_COMMENT_SIZE = 200;

    public CreateCommentResponse create(CreateCommentRequest createCommentRequest) throws NotFoundException, AccountNotFoundException, MissingParameterException {
        if(createCommentRequest.getComment() == null ||
           createCommentRequest.getId() == null) throw new MissingParameterException("Parameter is not enough.");

        Optional<Post> postRs = this.postService.findById(createCommentRequest.getId());

        if(postRs.isEmpty()) throw new NotFoundException("Post is not existed.");

        Optional<Account> accountRs = this.accountService.findById(createCommentRequest.getAccountId());

        if(accountRs.isEmpty()) throw new AccountNotFoundException("User is not validated.");

        if(createCommentRequest.getComment().length() > MAX_COMMENT_SIZE) throw new IllegalArgumentException("Parameter value is invalid.");

        Comment comment = Comment.builder()
                .createdAt(LocalDateTime.now())
                .account(accountRs.get())
                .post(postRs.get())
                .content(createCommentRequest.getComment())
                .build();

        comment = this.commentService.saveOrUpdate(comment);

        return CreateCommentResponse.builder()
                .id(comment.getId())
                .comment(comment.getContent())
                .createdAt(comment.getCreatedAt().format(DateTimeFormatter.ISO_DATE_TIME))
                .poster(
                        Poster.builder()
                                .id(accountRs.get().getId())
                                .name(accountRs.get().getProfile().getUsername())
                                .avatar(accountRs.get().getProfile().getAvatarLink())
                                .build()
                )
                .build();
    }

}
