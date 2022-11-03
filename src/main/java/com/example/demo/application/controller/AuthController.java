package com.example.demo.application.controller;

import com.example.demo.domain.model.ResponseBody;
import com.example.demo.domain.model.SignInRequest;
import com.example.demo.domain.model.SignUpRequest;
import com.example.demo.domain.service.sign_in.SignInService;
import com.example.demo.domain.service.sign_out.SignOutService;
import com.example.demo.domain.service.sign_up.SignUpService;
import com.example.demo.exception.ExistedResourceException;
import com.example.demo.exception.MissingParameterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private SignUpService signUpService;

    @Autowired
    private SignInService signInService;

    @Autowired
    private SignOutService signOutService;

    @PostMapping(path = "/signup")
    public ResponseEntity<Object> signUp(@RequestBody SignUpRequest signUpRequest) {
        try {
            return new ResponseEntity<>(
                    ResponseBody.builder()
                            .code(1000)
                            .message("OK")
                            .data(this.signUpService.signUp(signUpRequest))
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
        catch (ExistedResourceException e) {
            return new ResponseEntity<>(
                    ResponseBody.builder()
                            .code(9996)
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

    @PostMapping(path = "/signin")
    public ResponseEntity<Object> signIn(@RequestBody SignInRequest request) {
        try {
            return new ResponseEntity<>(
                    ResponseBody.builder()
                            .code(1000)
                            .message("OK")
                            .data(this.signInService.signIn(request))
                            .build(),
                    HttpStatus.OK
            );
        }
        catch (IllegalArgumentException e) {
            return new ResponseEntity<>(
                    ResponseBody.builder()
                            .code(9995)
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

    @PostMapping(path = "/signout")
    public void signOut(@RequestHeader("Authorization") String bearer) {
        String token = bearer.substring("Bearer ".length());
        this.signOutService.signOut(token);
    }

}
