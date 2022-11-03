package com.example.demo.infrastructure.repository;

import com.example.demo.domain.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    public boolean existsByPhoneNumber(String phoneNumber);

    public boolean existsByUuid(String uuid);

    public Optional<Account> findByPhoneNumber(String phoneNumber);

    @Query("SELECT a FROM account a LEFT JOIN FETCH a.likedPosts WHERE a.id = :id")
    public Optional<Account> findByIdWithLikedPostsLoadedEagerly(@Param("id") Long id);

}
