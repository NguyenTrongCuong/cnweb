package com.example.demo.infrastructure.repository;

import com.example.demo.domain.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    @Query("SELECT p FROM profile p JOIN p.account a WHERE a.phoneNumber = :phoneNumber")
    public Optional<Profile> findByAccountPhoneNumber(@Param("phoneNumber") String phoneNumber);

    @Query("SELECT p FROM profile p JOIN p.account a WHERE a.id = :id")
    public Optional<Profile> findByAccountId(@Param("id") Long id);

}
