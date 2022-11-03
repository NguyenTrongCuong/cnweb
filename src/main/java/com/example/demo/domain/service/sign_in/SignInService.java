package com.example.demo.domain.service.sign_in;

import com.example.demo.domain.entity.Account;
import com.example.demo.domain.entity.Session;
import com.example.demo.domain.model.SignInRequest;
import com.example.demo.domain.model.SignInResponse;
import com.example.demo.domain.service.crud.AccountService;
import com.example.demo.domain.service.crud.SessionService;
import com.example.demo.exception.MissingParameterException;
import com.example.demo.utils.EncryptionUtils;
import com.example.demo.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service("signInService")
@Transactional
public class SignInService {

    @Autowired
    private AccountService accountService;

    @Autowired
    private SessionService sessionService;

    public SignInResponse signIn(SignInRequest request) throws MissingParameterException {
        if(request.getPhoneNumber() == null ||
           request.getPassword() == null ||
           request.getUuid() == null) throw new MissingParameterException("Parameter is not enough.");

        Optional<Account> rs = this.accountService.findByPhoneNumber(request.getPhoneNumber());

        if(rs.isEmpty()) throw new IllegalArgumentException("User is not validated.");

        Account actual = rs.get();

        if(!EncryptionUtils.verify(request.getPassword(), actual.getPassword()))
            throw new IllegalArgumentException("User is not validated.");

        if(!actual.getUuid().equals(request.getUuid()))
            throw new IllegalArgumentException("User is not validated.");

        if(actual.getSession() != null) this.sessionService.delete(actual.getSession());

        String token = TokenUtils.generateToken(actual.getId());

        Session session = new Session();
        session.setSessionId(token);
        session.setAccount(actual);
        this.sessionService.saveOrUpdate(session);

        return SignInResponse.builder()
                .id(actual.getId())
                .username(actual.getProfile().getUsername())
                .avatar(actual.getProfile().getAvatarLink())
                .token(token)
                .build();
    }

}
