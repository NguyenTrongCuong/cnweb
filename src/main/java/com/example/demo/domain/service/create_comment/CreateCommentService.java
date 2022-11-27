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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CreateCommentService {

    @Autowired
    private CommentService commentService;

    @Autowired
    private PostService postService;

    @Autowired
    private AccountService accountService;

    private static final int MAX_COMMENT_SIZE = 200;

    public List<CreateCommentResponse> create(CreateCommentRequest createCommentRequest) throws NotFoundException, AccountNotFoundException, MissingParameterException {
        Integer index = createCommentRequest.getIndex();
        Integer count = createCommentRequest.getCount();

        if(createCommentRequest.getComment() == null ||
           createCommentRequest.getId() == null ||
           index == null ||
           count == null) throw new MissingParameterException("Parameter is not enough.");

        if(index <= 0 || count <= 0) throw new IllegalArgumentException("Parameter value is invalid.");

        Optional<Post> postRs = this.postService.findById(createCommentRequest.getId());

        if(postRs.isEmpty()) throw new NotFoundException("Post is not existed.");

        Optional<Account> accountRs = this.accountService.findById(createCommentRequest.getAccountId());

        if(accountRs.isEmpty()) throw new AccountNotFoundException("User is not validated.");

        if(createCommentRequest.getComment().length() > MAX_COMMENT_SIZE ||
           createCommentRequest.getComment().length() == 0) throw new IllegalArgumentException("Parameter value is invalid.");

        Comment newComment = Comment.builder()
                .createdAt(LocalDateTime.now())
                .account(accountRs.get())
                .post(postRs.get())
                .content(createCommentRequest.getComment())
                .build();

        this.commentService.saveOrUpdate(newComment);

        Pageable pageable = PageRequest.of(index - 1, count, Sort.by(Sort.Order.desc("id")));

        List<Comment> comments = this.commentService.findByPostId(createCommentRequest.getId(), pageable);

        return comments.stream()
                .map(comment -> {
                    return CreateCommentResponse.builder()
                            .id(comment.getId().toString())
                            .comment(comment.getContent())
                            .createdAt(comment.getCreatedAt().format(DateTimeFormatter.ISO_DATE_TIME))
                            .poster(
                                    Poster.builder()
                                            .id(comment.getAccount().getId().toString())
                                            .name(comment.getAccount().getProfile().getUsername())
                                            .avatar(comment.getAccount().getProfile().getAvatarLink())
                                            .online("online")
                                            .build()
                            )
                            .build();
                })
                .collect(Collectors.toList());

//        return CreateCommentResponse.builder()
//                .id(comment.getId().toString())
//                .comment(comment.getContent())
//                .createdAt(comment.getCreatedAt().format(DateTimeFormatter.ISO_DATE_TIME))
//                .poster(
//                        Poster.builder()
//                                .id(accountRs.get().getId().toString())
//                                .name(accountRs.get().getProfile().getUsername())
//                                .avatar(accountRs.get().getProfile().getAvatarLink())
//                                .build()
//                )
//                .build();
    }

}
