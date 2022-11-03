package com.example.demo.infrastructure.repository;

import com.example.demo.domain.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, Long> {

    public void deleteBySessionId(String sessionId);

    public boolean existsBySessionId(String sessionId);

}
