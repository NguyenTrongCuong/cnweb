package com.example.demo.infrastructure.repository;

import com.example.demo.domain.entity.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {

}
