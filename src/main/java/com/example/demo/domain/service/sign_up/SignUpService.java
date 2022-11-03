package com.example.demo.domain.service.sign_up;

import com.example.demo.domain.entity.Account;
import com.example.demo.domain.entity.Profile;
import com.example.demo.domain.entity.VerificationCode;
import com.example.demo.domain.model.SignUpRequest;
import com.example.demo.domain.model.SignUpResponse;
import com.example.demo.domain.service.crud.AccountService;
import com.example.demo.domain.service.crud.ProfileService;
import com.example.demo.domain.service.crud.VerificationCodeService;
import com.example.demo.exception.ExistedResourceException;
import com.example.demo.exception.MissingParameterException;
import com.example.demo.utils.EncryptionUtils;
import com.example.demo.utils.ValidationUtils;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("signUpService")
@Transactional
public class SignUpService {

    @Autowired
    private AccountService accountService;

    @Autowired
    private VerificationCodeService verificationCodeService;

    @Autowired
    private ProfileService profileService;

    public SignUpResponse signUp(SignUpRequest request) throws ExistedResourceException, MissingParameterException {
        if(request.getPhoneNumber() == null ||
           request.getPassword() == null ||
           request.getUuid() == null) throw new MissingParameterException("Parameter is not enough.");

        if(!ValidationUtils.isValidPhoneNumber(request.getPhoneNumber()) ||
            !ValidationUtils.isValidPassword(request.getPassword()) ||
            !ValidationUtils.isValidUuid(request.getUuid())) throw new IllegalArgumentException("Parameter value is invalid.");

        if(this.accountService.existsByPhoneNumber(request.getPhoneNumber()))
            throw new ExistedResourceException("User existed.");

        Account account = new Account();
        account.setPhoneNumber(request.getPhoneNumber());
        account.setPassword(request.getPassword());
        account.setUuid(request.getUuid());

        account.setPassword(EncryptionUtils.encrypt(account.getPassword()));
        account = this.accountService.saveOrUpdate(account);

        String code = this.generateVerificationCode();

        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setCode(code);
        verificationCode.setAccount(account);
        this.verificationCodeService.saveOrUpdate(verificationCode);

        Profile profile = new Profile();
        profile.setAccount(account);
        profile.setAvatarLink("-1");
        profile.setUsername("-1");
        this.profileService.saveOrUpdate(profile);

        return SignUpResponse.builder()
                .verificationCode(code)
                .build();
    }

    private String generateVerificationCode() {
        CharacterRule alphabets = new CharacterRule(EnglishCharacterData.Alphabetical);
        CharacterRule digits = new CharacterRule(EnglishCharacterData.Digit);

        PasswordGenerator passwordGenerator = new PasswordGenerator();
        return passwordGenerator.generatePassword(6, alphabets, digits);
    }

}
