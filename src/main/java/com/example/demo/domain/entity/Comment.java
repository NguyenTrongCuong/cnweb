package com.example.demo.domain.entity;

import lombok.Builder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "comment")
@Builder
public class Comment extends Base {

    @Column(name = "content")
    private String content;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Comment() {}

    public Comment(String content, LocalDateTime createdAt, Post post, Account account) {
        this.content = content;
        this.createdAt = createdAt;
        this.post = post;
        this.account = account;
    }

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
