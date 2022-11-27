package com.example.demo.infrastructure.repository;

import com.example.demo.domain.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Long> {

    @Query("SELECT r FROM report r WHERE r.account.id = :accountId AND r.post.id = :postId")
    public Optional<Report> findByAccountIdAndPostId(Long accountId, Long postId);

}
