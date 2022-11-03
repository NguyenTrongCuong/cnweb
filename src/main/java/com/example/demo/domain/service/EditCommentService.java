package com.example.demo.domain.service;

import com.example.demo.domain.entity.Account;
import com.example.demo.domain.entity.Comment;
import com.example.demo.domain.model.CreateCommentRequest;
import com.example.demo.domain.model.CreateCommentResponse;
import com.example.demo.domain.model.Poster;
import com.example.demo.domain.service.crud.AccountService;
import com.example.demo.domain.service.crud.CommentService;
import com.example.demo.exception.MissingParameterException;
import com.example.demo.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class EditCommentService {

    @Autowired
    private CommentService commentService;

    @Autowired
    private AccountService accountService;

    private static final int MAX_COMMENT_SIZE = 200;

    public CreateCommentResponse edit(CreateCommentRequest createCommentRequest) throws MissingParameterException {
        if(createCommentRequest.getComment() == null ||
           createCommentRequest.getId() == null) throw new MissingParameterException("Parameter is not enough.");

        Optional<Comment> commentRs = this.commentService.findById(createCommentRequest.getId());

        if(commentRs.isEmpty()) throw new IllegalArgumentException("Parameter value is invalid.");

        Account account = commentRs.get().getAccount();

        if(!account.getId().equals(createCommentRequest.getAccountId())) throw new IllegalArgumentException("Parameter value is invalid.");

        if(createCommentRequest.getComment().length() > MAX_COMMENT_SIZE) throw new IllegalArgumentException("Parameter value is invalid.");

        Comment comment = commentRs.get();
        comment.setContent(createCommentRequest.getComment());
        comment = this.commentService.saveOrUpdate(comment);

        return CreateCommentResponse.builder()
                .id(comment.getId())
                .comment(comment.getContent())
                .createdAt(comment.getCreatedAt().format(DateTimeFormatter.ISO_DATE_TIME))
                .poster(
                        Poster.builder()
                                .id(account.getId())
                                .name(account.getProfile().getUsername())
                                .avatar(account.getProfile().getAvatarLink())
                                .build()
                )
                .build();
    }

}
