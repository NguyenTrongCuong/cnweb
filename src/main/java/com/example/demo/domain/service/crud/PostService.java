package com.example.demo.domain.service.crud;

import com.example.demo.domain.entity.Post;
import com.example.demo.infrastructure.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service("postService")
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public Post saveOrUpdate(Post post) {
        return this.postRepository.save(post);
    }

    public List<Post> saveOrUpdateAll(Collection<Post> posts) {
        return this.postRepository.saveAll(posts);
    }

    public Optional<Post> findById(Long id) {
        return this.postRepository.findById(id);
    }

    public Optional<Post> findByIdWithAllRelationshipsLoadedEagerly(Long id) {
        return this.postRepository.findByIdWithAllRelationshipsLoadedEagerly(id);
    }

    public void delete(Post post) {
        this.postRepository.delete(post);
    }

    public List<Post> findByAccountIdWithAllRelationshipsLoadedEagerly(Long id, Pageable pageable) {
        return this.postRepository.findByAccountIdWithAllRelationshipsLoadedEagerly(id, pageable);
    }

    public List<Post> findByIdAfter(Long id) {
        return this.postRepository.findByIdAfter(id);
    }

}
