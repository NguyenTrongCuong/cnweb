package com.example.demo.application.controller;

import com.example.demo.domain.model.CreateCommentRequest;
import com.example.demo.domain.model.GetCommentRequest;
import com.example.demo.domain.model.ResponseBody;
import com.example.demo.domain.service.EditCommentService;
import com.example.demo.domain.service.create_comment.CreateCommentService;
import com.example.demo.domain.service.get_comment.GetCommentService;
import com.example.demo.exception.AccountNotFoundException;
import com.example.demo.exception.MissingParameterException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommentController {

    @Autowired
    private CreateCommentService createCommentService;

    @Autowired
    private EditCommentService editCommentService;

    @Autowired
    private GetCommentService getCommentService;

    @PostMapping(path = "/set_comment")
    public ResponseEntity<Object> create(@RequestHeader("Authorization") String bearer, @RequestBody CreateCommentRequest createCommentRequest) {
        Long accountId = Long.valueOf(TokenUtils.decodeToken(bearer.substring("Bearer ".length())).getSubject());
        createCommentRequest.setAccountId(accountId);
        try {
            return new ResponseEntity<>(
                    ResponseBody.builder()
                            .code(1000)
                            .message("OK")
                            .data(this.createCommentService.create(createCommentRequest))
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
                            .code(1002)
                            .message(e.getMessage())
                            .build(),
                    HttpStatus.BAD_REQUEST
            );
        }
        catch (AccountNotFoundException e) {
            return new ResponseEntity<>(
                    ResponseBody.builder()
                            .code(9995)
                            .message(e.getMessage())
                            .build(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @PostMapping(path = "/edit_comment")
    public ResponseEntity<Object> edit(@RequestHeader("Authorization") String bearer, @RequestBody CreateCommentRequest createCommentRequest) {
        Long accountId = Long.valueOf(TokenUtils.decodeToken(bearer.substring("Bearer ".length())).getSubject());
        createCommentRequest.setAccountId(accountId);
        try {
            return new ResponseEntity<>(
                    ResponseBody.builder()
                            .code(1000)
                            .message("OK")
                            .data(this.editCommentService.edit(createCommentRequest))
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

    @GetMapping(path = "/get_comment")
    public ResponseEntity<Object> get(@RequestBody GetCommentRequest getCommentRequest) {
        try {
            return new ResponseEntity<>(
                    ResponseBody.builder()
                            .code(1000)
                            .message("OK")
                            .data(this.getCommentService.get(getCommentRequest))
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
        catch (NotFoundException e) {
            return new ResponseEntity<>(
                    ResponseBody.builder()
                            .code(9992)
                            .message(e.getMessage())
                            .build(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

}
