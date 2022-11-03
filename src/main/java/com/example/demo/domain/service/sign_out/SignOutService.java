package com.example.demo.domain.service.sign_out;

import com.example.demo.domain.service.crud.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("signOutService")
@Transactional
public class SignOutService {

    @Autowired
    private SessionService sessionService;

    public void signOut(String token) {
        this.sessionService.deleteByToken(token);
    }

}
