package com.example.demo.domain.service.crud;

import com.example.demo.domain.entity.Session;
import com.example.demo.infrastructure.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service("sessionService")
@Transactional
public class SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    public Session saveOrUpdate(Session session) {
        return this.sessionRepository.save(session);
    }

    public List<Session> saveOrUpdateAll(Collection<Session> sessions) {
        return this.sessionRepository.saveAll(sessions);
    }

    public void delete(Session session) {
        this.sessionRepository.delete(session);
    }

    public void deleteByToken(String token) {
        this.sessionRepository.deleteBySessionId(token);
    }

    public boolean existsBySessionId(String sessionId) {
        return this.sessionRepository.existsBySessionId(sessionId);
    }

}
