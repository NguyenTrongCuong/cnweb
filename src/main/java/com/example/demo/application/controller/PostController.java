package com.example.demo.application.controller;

import com.example.demo.domain.entity.Post;
import com.example.demo.domain.model.*;
import com.example.demo.domain.model.ResponseBody;
import com.example.demo.domain.service.check_new_item.CheckNewItemService;
import com.example.demo.domain.service.create_post.CreatePostService;
import com.example.demo.domain.service.create_report.CreateReportService;
import com.example.demo.domain.service.delete_post.DeletePostService;
import com.example.demo.domain.service.get_list_posts.GetListPostsService;
import com.example.demo.domain.service.like_post.LikePostService;
import com.example.demo.domain.service.update_post.UpdatePostService;
import com.example.demo.exception.*;
import com.example.demo.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class PostController {

    @Autowired
    private CreatePostService createPostService;

    @Autowired
    private DeletePostService deletePostService;

    @Autowired
    private UpdatePostService updatePostService;

    @Autowired
    private CreateReportService createReportService;

    @Autowired
    private LikePostService likePostService;

    @Autowired
    private GetListPostsService getListPostsService;

    @Autowired
    private CheckNewItemService checkNewItemService;

    @PostMapping(path = "/add_post")
    public ResponseEntity<Object> createPost(@RequestHeader("Authorization") String bearer, Post post) {
        Long accountId = Long.valueOf(TokenUtils.decodeToken(bearer.substring("Bearer ".length())).getSubject());
        try {
            return new ResponseEntity<>(
                    ResponseBody.builder()
                            .code(1000)
                            .message("OK")
                            .data(this.createPostService.create(accountId, post))
                            .build(),
                    HttpStatus.OK
            );
        }
        catch (IllegalArgumentException e) {
            return new ResponseEntity<>(
                    ResponseBody.builder()
                            .code(1004)
                            .message(e.getMessage())
                            .build(),
                    HttpStatus.BAD_REQUEST
            );
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        catch (MissingParameterException e) {
            return new ResponseEntity<>(
                    ResponseBody.builder()
                            .code(1002)
                            .message(e.getMessage())
                            .build(),
                    HttpStatus.BAD_REQUEST
            );
        }
        catch (FileQuantityException e) {
            return new ResponseEntity<>(
                    ResponseBody.builder()
                            .code(1008)
                            .message(e.getMessage())
                            .build(),
                    HttpStatus.BAD_REQUEST
            );
        }
        catch (FileSizeException e) {
            return new ResponseEntity<>(
                    ResponseBody.builder()
                            .code(1006)
                            .message(e.getMessage())
                            .build(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @PostMapping(path = "/delete_post")
    public ResponseEntity<Object> delete(@RequestBody DeletePostRequest deletePostRequest) {
        try {
            return new ResponseEntity<>(
                    ResponseBody.builder()
                            .code(1000)
                            .message("OK")
                            .data(this.deletePostService.delete(deletePostRequest))
                            .build(),
                    HttpStatus.OK
            );
        }
        catch (IllegalArgumentException e) {
            return new ResponseEntity<>(
                    ResponseBody.builder()
                            .code(9992)
                            .message(e.getMessage())
                            .build(),
                    HttpStatus.BAD_REQUEST
            );
        }
        catch (MissingParameterException e) {
            return new ResponseEntity<>(
                    ResponseBody.builder()
                            .code(9992)
                            .message(e.getMessage())
                            .build(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @PostMapping(path = "/edit_post")
    public ResponseEntity<Object> edit(@RequestHeader("Authorization") String bearer, EditPostRequest editPostRequest) {
        Long accountId = Long.valueOf(TokenUtils.decodeToken(bearer.substring("Bearer ".length())).getSubject());
        try {
            return new ResponseEntity<>(
                    ResponseBody.builder()
                            .code(1000)
                            .message("OK")
                            .data(this.updatePostService.update(accountId, editPostRequest))
                            .build(),
                    HttpStatus.OK
            );
        }
        catch (IllegalArgumentException e) {
            return new ResponseEntity<>(
                    ResponseBody.builder()
                            .code(1004)
                            .message(e.getMessage())
                            .build(),
                    HttpStatus.BAD_REQUEST
            );
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        catch (NotFoundException e) {
            return new ResponseEntity<>(
                    ResponseBody.builder()
                            .code(9992)
                            .message(e.getMessage())
                            .build(),
                    HttpStatus.BAD_REQUEST
            );
        }
        catch (AccessDeniedException e) {
            return new ResponseEntity<>(
                    ResponseBody.builder()
                            .code(1009)
                            .message(e.getMessage())
                            .build(),
                    HttpStatus.BAD_REQUEST
            );
        }
        catch (MissingParameterException e) {
            return new ResponseEntity<>(
                    ResponseBody.builder()
                            .code(9992)
                            .message(e.getMessage())
                            .build(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @PostMapping(path = "/report_post")
    public ResponseEntity<Object> edit(@RequestHeader("Authorization") String bearer, @RequestBody CreateReportRequest createReportRequest) {
        Long accountId = Long.valueOf(TokenUtils.decodeToken(bearer.substring("Bearer ".length())).getSubject());
        try {
            return new ResponseEntity<>(
                    ResponseBody.builder()
                            .code(1000)
                            .message("OK")
                            .data(this.createReportService.create(accountId, createReportRequest))
                            .build(),
                    HttpStatus.OK
            );
        }
        catch (IllegalArgumentException e) {
            return new ResponseEntity<>(
                    ResponseBody.builder()
                            .code(1004)
                            .message(e.getMessage())
                            .build(),
                    HttpStatus.BAD_REQUEST
            );
        }
        catch (NotFoundException e) {
            return new ResponseEntity<>(
                    ResponseBody.builder()
                            .code(9992)
                            .message(e.getMessage())
                            .build(),
                    HttpStatus.BAD_REQUEST
            );
        }
        catch (MissingParameterException e) {
            return new ResponseEntity<>(
                    ResponseBody.builder()
                            .code(9992)
                            .message(e.getMessage())
                            .build(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @PostMapping(path = "/like")
    public ResponseEntity<Object> like(@RequestHeader("Authorization") String bearer, @RequestBody LikePostRequest likePostRequest) {
        Long accountId = Long.valueOf(TokenUtils.decodeToken(bearer.substring("Bearer ".length())).getSubject());
        try {
            return new ResponseEntity<>(
                    ResponseBody.builder()
                            .code(1000)
                            .message("OK")
                            .data(this.likePostService.like(accountId, likePostRequest))
                            .build(),
                    HttpStatus.OK
            );
        }
        catch (NotFoundException e) {
            return new ResponseEntity<>(
                    ResponseBody.builder()
                            .code(9992)
                            .message(e.getMessage())
                            .build(),
                    HttpStatus.BAD_REQUEST
            );
        }
        catch (MissingParameterException e) {
            return new ResponseEntity<>(
                    ResponseBody.builder()
                            .code(9992)
                            .message(e.getMessage())
                            .build(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @GetMapping(path = "/get_list_posts")
    public ResponseEntity<Object> getListPosts(@RequestHeader("Authorization") String bearer, @RequestBody GetListPostsRequest getListPostsRequest) {
        Long accountId = Long.valueOf(TokenUtils.decodeToken(bearer.substring("Bearer ".length())).getSubject());
        try {
            return new ResponseEntity<>(
                    ResponseBody.builder()
                            .code(1000)
                            .message("OK")
                            .data(this.getListPostsService.get(accountId, getListPostsRequest))
                            .build(),
                    HttpStatus.OK
            );
        }
        catch (IllegalArgumentException e) {
            return new ResponseEntity<>(
                    ResponseBody.builder()
                            .code(1004)
                            .message(e.getMessage())
                            .build(),
                    HttpStatus.BAD_REQUEST
            );
        }
        catch (MissingParameterException e) {
            return new ResponseEntity<>(
                    ResponseBody.builder()
                            .code(1002)
                            .message(e.getMessage())
                            .build(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @GetMapping(path = "/get_list_posts_v2")
    public ResponseEntity<Object> getListPostsV2(@RequestHeader("Authorization") String bearer, @RequestBody GetListPostsRequest getListPostsRequest) {
        Long accountId = Long.valueOf(TokenUtils.decodeToken(bearer.substring("Bearer ".length())).getSubject());
        try {
            return new ResponseEntity<>(
                    ResponseBody.builder()
                            .code(1000)
                            .message("OK")
                            .data(this.getListPostsService.getV2(accountId, getListPostsRequest))
                            .build(),
                    HttpStatus.OK
            );
        }
        catch (IllegalArgumentException e) {
            return new ResponseEntity<>(
                    ResponseBody.builder()
                            .code(1004)
                            .message(e.getMessage())
                            .build(),
                    HttpStatus.BAD_REQUEST
            );
        }
        catch (MissingParameterException e) {
            return new ResponseEntity<>(
                    ResponseBody.builder()
                            .code(1002)
                            .message(e.getMessage())
                            .build(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @GetMapping(path = "/check_new_item")
    public ResponseEntity<Object> checkNewItem(@RequestHeader("Authorization") String bearer, @RequestBody CheckNewItemRequest checkNewItemRequest) {
        Long accountId = Long.valueOf(TokenUtils.decodeToken(bearer.substring("Bearer ".length())).getSubject());
        try {
            return new ResponseEntity<>(
                    ResponseBody.builder()
                            .code(1000)
                            .message("OK")
                            .data(this.checkNewItemService.check(accountId, checkNewItemRequest))
                            .build(),
                    HttpStatus.OK
            );
        }
        catch (IllegalArgumentException e) {
            return new ResponseEntity<>(
                    ResponseBody.builder()
                            .code(1004)
                            .message(e.getMessage())
                            .build(),
                    HttpStatus.BAD_REQUEST
            );
        }
        catch (MissingParameterException e) {
            return new ResponseEntity<>(
                    ResponseBody.builder()
                            .code(1002)
                            .message(e.getMessage())
                            .build(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

}
