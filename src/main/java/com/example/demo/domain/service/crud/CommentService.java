package com.example.demo.domain.service.crud;

import com.example.demo.domain.entity.Comment;
import com.example.demo.infrastructure.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public Comment saveOrUpdate(Comment comment) {
        return this.commentRepository.save(comment);
    }

    public Optional<Comment> findById(Long id) {
        return this.commentRepository.findById(id);
    }

    public List<Comment> findByPostId(Long id, Pageable pageable) {
        return this.commentRepository.findByPostId(id, pageable);
    }

    public void deleteAll(Collection<Comment> comments) {
        this.commentRepository.deleteAll(comments);
    }

}
