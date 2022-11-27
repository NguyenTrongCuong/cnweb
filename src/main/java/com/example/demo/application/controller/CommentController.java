package com.example.demo.application.controller;

import com.example.demo.domain.model.CreateCommentRequest;
import com.example.demo.domain.model.GetCommentRequest;
import com.example.demo.domain.model.ResponseBody;
import com.example.demo.domain.service.edit_comment.EditCommentService;
import com.example.demo.domain.service.create_comment.CreateCommentService;
import com.example.demo.domain.service.get_comment.GetCommentService;
import com.example.demo.exception.AccessDeniedException;
import com.example.demo.exception.AccountNotFoundException;
import com.example.demo.exception.MissingParameterException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.utils.InputUtils;
import com.example.demo.utils.TokenUtils;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class CommentController {

    @Autowired
    private CreateCommentService createCommentService;

    @Autowired
    private EditCommentService editCommentService;

    @Autowired
    private GetCommentService getCommentService;

    @PostMapping(path = "/set_comment")
    public ResponseEntity<Object> create(HttpServletRequest request) {
        Long accountId = Long.valueOf(TokenUtils.decodeToken((String) request.getAttribute("token")).getSubject());
        try {
            CreateCommentRequest createCommentRequest = InputUtils.buildCreateCommentRequest(request);
            createCommentRequest.setAccountId(accountId);
            return new ResponseEntity<>(
                    ResponseBody.builder()
                            .code("1000")
                            .message("OK")
                            .data(this.createCommentService.create(createCommentRequest))
                            .build(),
                    HttpStatus.OK
            );
        }
        catch (NotFoundException e) {
            return new ResponseEntity<>(
                    ResponseBody.builder()
                            .code("9992")
                            .message(e.getMessage())
                            .build(),
                    HttpStatus.BAD_REQUEST
            );
        }
        catch (MissingParameterException e) {
            return new ResponseEntity<>(
                    ResponseBody.builder()
                            .code("1002")
                            .message(e.getMessage())
                            .build(),
                    HttpStatus.BAD_REQUEST
            );
        }
        catch (AccountNotFoundException e) {
            return new ResponseEntity<>(
                    ResponseBody.builder()
                            .code("9995")
                            .message(e.getMessage())
                            .build(),
                    HttpStatus.BAD_REQUEST
            );
        }
        catch (JSONException e) {
            return new ResponseEntity<>(
                    ResponseBody.builder()
                            .code("1003")
                            .message("Parameter type is invalid.")
                            .build(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @PostMapping(path = "/edit_comment")
    public ResponseEntity<Object> edit(HttpServletRequest request) {
        Long accountId = Long.valueOf(TokenUtils.decodeToken((String) request.getAttribute("token")).getSubject());
        try {
            CreateCommentRequest createCommentRequest = InputUtils.buildCreateCommentRequest(request);
            createCommentRequest.setAccountId(accountId);
            return new ResponseEntity<>(
                    ResponseBody.builder()
                            .code("1000")
                            .message("OK")
                            .data(this.editCommentService.edit(createCommentRequest))
                            .build(),
                    HttpStatus.OK
            );
        }
        catch (IllegalArgumentException e) {
            return new ResponseEntity<>(
                    ResponseBody.builder()
                            .code("1004")
                            .message(e.getMessage())
                            .build(),
                    HttpStatus.BAD_REQUEST
            );
        }
        catch (MissingParameterException e) {
            return new ResponseEntity<>(
                    ResponseBody.builder()
                            .code("1002")
                            .message(e.getMessage())
                            .build(),
                    HttpStatus.BAD_REQUEST
            );
        }
        catch (AccessDeniedException e) {
            return new ResponseEntity<>(
                    ResponseBody.builder()
                            .code("1009")
                            .message(e.getMessage())
                            .build(),
                    HttpStatus.FORBIDDEN
            );
        }
        catch (JSONException e) {
            return new ResponseEntity<>(
                    ResponseBody.builder()
                            .code("1003")
                            .message("Parameter type is invalid.")
                            .build(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @PostMapping(path = "/get_comment")
    public ResponseEntity<Object> get(HttpServletRequest request) {
        try {
            GetCommentRequest getCommentRequest = InputUtils.buildGetCommentRequest(request);
            return new ResponseEntity<>(
                    ResponseBody.builder()
                            .code("1000")
                            .message("OK")
                            .data(this.getCommentService.get(getCommentRequest))
                            .build(),
                    HttpStatus.OK
            );
        }
        catch (IllegalArgumentException e) {
            return new ResponseEntity<>(
                    ResponseBody.builder()
                            .code("1004")
                            .message(e.getMessage())
                            .build(),
                    HttpStatus.BAD_REQUEST
            );
        }
        catch (MissingParameterException e) {
            return new ResponseEntity<>(
                    ResponseBody.builder()
                            .code("1002")
                            .message(e.getMessage())
                            .build(),
                    HttpStatus.BAD_REQUEST
            );
        }
        catch (NotFoundException e) {
            return new ResponseEntity<>(
                    ResponseBody.builder()
                            .code("9992")
                            .message(e.getMessage())
                            .build(),
                    HttpStatus.BAD_REQUEST
            );
        }
        catch (JSONException e) {
            return new ResponseEntity<>(
                    ResponseBody.builder()
                            .code("1003")
                            .message("Parameter type is invalid.")
                            .build(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

}
