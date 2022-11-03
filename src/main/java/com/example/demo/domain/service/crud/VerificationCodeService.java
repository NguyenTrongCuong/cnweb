package com.example.demo.domain.service.crud;

import com.example.demo.domain.entity.VerificationCode;
import com.example.demo.infrastructure.repository.VerificationCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service("verificationCodeService")
@Transactional
public class VerificationCodeService {

    @Autowired
    private VerificationCodeRepository verificationCodeRepository;

    public VerificationCode saveOrUpdate(VerificationCode verificationCode) {
        return this.verificationCodeRepository.save(verificationCode);
    }

    public List<VerificationCode> saveOrUpdateAll(Collection<VerificationCode> verificationCodes) {
        return this.verificationCodeRepository.saveAll(verificationCodes);
    }

}
