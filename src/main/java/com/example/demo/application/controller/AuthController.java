package com.example.demo.application.controller;

import com.example.demo.domain.model.ResponseBody;
import com.example.demo.domain.model.SignInRequest;
import com.example.demo.domain.model.SignUpRequest;
import com.example.demo.domain.service.sign_in.SignInService;
import com.example.demo.domain.service.sign_out.SignOutService;
import com.example.demo.domain.service.sign_up.SignUpService;
import com.example.demo.exception.ExistedResourceException;
import com.example.demo.exception.MissingParameterException;
import com.example.demo.utils.InputUtils;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class AuthController {

    @Autowired
    private SignUpService signUpService;

    @Autowired
    private SignInService signInService;

    @Autowired
    private SignOutService signOutService;

    @PostMapping(path = "/signup")
    public ResponseEntity<Object> signUp(HttpServletRequest request) {
        try {
            SignUpRequest signUpRequest = InputUtils.buildSignUpRequest(request);
            return new ResponseEntity<>(
                    ResponseBody.builder()
                            .code("1000")
                            .message("OK")
                            .data(this.signUpService.signUp(signUpRequest))
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
        catch (ExistedResourceException e) {
            return new ResponseEntity<>(
                    ResponseBody.builder()
                            .code("9996")
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

    @PostMapping(path = "/login")
    public ResponseEntity<Object> signIn(HttpServletRequest request) {
        try {
            SignInRequest signInRequest = InputUtils.buildSignInRequest(request);
            return new ResponseEntity<>(
                    ResponseBody.builder()
                            .code("1000")
                            .message("OK")
                            .data(this.signInService.signIn(signInRequest))
                            .build(),
                    HttpStatus.OK
            );
        }
        catch (IllegalArgumentException e) {
            return new ResponseEntity<>(
                    ResponseBody.builder()
                            .code("9995")
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

    @PostMapping(path = "/logout")
    public void signOut(HttpServletRequest request) {
        String token = (String) request.getAttribute("token");
        this.signOutService.signOut(token);
    }

}
