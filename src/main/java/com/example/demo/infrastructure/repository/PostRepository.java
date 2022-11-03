package com.example.demo.infrastructure.repository;

import com.example.demo.domain.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM post p LEFT JOIN FETCH p.staticResources LEFT JOIN FETCH p.supporters LEFT JOIN FETCH p.comments LEFT JOIN FETCH p.reports r WHERE p.id = :id")
    public Optional<Post> findByIdWithAllRelationshipsLoadedEagerly(@Param("id") Long id);

    @Query("SELECT p FROM post p LEFT JOIN FETCH p.staticResources LEFT JOIN FETCH p.comments LEFT JOIN FETCH p.supporters JOIN p.account a WHERE a.id = :id")
    public List<Post> findByAccountIdWithAllRelationshipsLoadedEagerly(@Param("id") Long id, Pageable pageable);

    @Query("SELECT p FROM post p LEFT JOIN FETCH p.staticResources LEFT JOIN FETCH p.comments LEFT JOIN FETCH p.supporters LEFT JOIN FETCH p.account WHERE p.id > :id")
    public List<Post> findByIdAfter(@Param("id") Long id);

}
