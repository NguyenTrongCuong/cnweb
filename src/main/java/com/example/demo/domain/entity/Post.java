package com.example.demo.domain.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity(name = "post")
public class Post extends Base {

    @Transient
    private List<MultipartFile> images;

    @Transient
    private List<MultipartFile> video;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private String status;

    @Column(name = "love")
    private Long love = 0L;

    @OneToMany(mappedBy = "post")
    private Set<StaticResource> staticResources;

    @OneToMany(mappedBy = "post")
    private Set<Comment> comments;

    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToMany(mappedBy = "post")
    private Set<Report> reports;

    @ManyToMany
    @JoinTable(name = "supporters",
               joinColumns = @JoinColumn(name = "post_id"),
               inverseJoinColumns = @JoinColumn(name = "account_id"))
    private Set<Account> supporters;

    public List<MultipartFile> getImages() {
        return images;
    }

    public void setImages(List<MultipartFile> images) {
        this.images = images;
    }

    public List<MultipartFile> getVideo() {
        return video;
    }

    public void setVideo(List<MultipartFile> video) {
        this.video = video;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getLove() {
        return love;
    }

    public void setLove(Long love) {
        this.love = love;
    }

    public Set<StaticResource> getStaticResources() {
        return staticResources;
    }

    public void setStaticResources(Set<StaticResource> staticResources) {
        this.staticResources = staticResources;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Set<Account> getSupporters() {
        return supporters;
    }

    public void setSupporters(Set<Account> supporters) {
        this.supporters = supporters;
    }

    public Set<Report> getReports() {
        return reports;
    }

    public void setReports(Set<Report> reports) {
        this.reports = reports;
    }
}
