package com.example.demo.infrastructure.repository;

import com.example.demo.domain.entity.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM comment c JOIN c.post p WHERE p.id = :id")
    public List<Comment> findByPostId(@Param("id") Long id, Pageable pageable);

}
