package com.example.demo.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "report")
@Builder
public class Report extends Base {

    @Column(name = "subject")
    private String subject;

    @Column(name = "details")
    private String details;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;

    public Report() {}

    public Report(String subject, String details, Post post, Account account) {
        this.subject = subject;
        this.details = details;
        this.post = post;
        this.account = account;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
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
