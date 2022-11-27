package com.example.demo.domain.service.get_comment;

import com.example.demo.domain.entity.Comment;
import com.example.demo.domain.model.CreateCommentResponse;
import com.example.demo.domain.model.GetCommentRequest;
import com.example.demo.domain.model.Poster;
import com.example.demo.domain.service.crud.CommentService;
import com.example.demo.domain.service.crud.PostService;
import com.example.demo.exception.MissingParameterException;
import com.example.demo.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service("getCommentService")
public class GetCommentService {

    @Autowired
    private CommentService commentService;

    @Autowired
    private PostService postService;

    public List<CreateCommentResponse> get(GetCommentRequest request) throws MissingParameterException, NotFoundException {
        Long id = request.getId();
        Integer index = request.getIndex();
        Integer count = request.getCount();

        if(id == null ||
           index == null ||
           count == null) throw new MissingParameterException("Parameter is not enough.");

        if(index <= 0 || count <= 0) throw new IllegalArgumentException("Parameter value is invalid.");

        if(this.postService.findById(request.getId()).isEmpty()) throw new NotFoundException("Post is not existed.");

        Pageable pageable = PageRequest.of(index - 1, count, Sort.by(Sort.Order.desc("id")));

        List<Comment> comments = this.commentService.findByPostId(id, pageable);

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
    }

}
