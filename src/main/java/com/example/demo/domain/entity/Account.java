package com.example.demo.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "account")
public class Account extends Base {

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "password")
    private String password;

    @Column(name = "uuid")
    private String uuid;

    @OneToOne(mappedBy = "account")
    private Profile profile;

    @OneToOne(mappedBy = "account")
    private Session session;

    @OneToMany(mappedBy = "account")
    private Set<VerificationCode> verificationCodes;

    @ManyToMany(mappedBy = "supporters")
    private Set<Post> likedPosts;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Set<VerificationCode> getVerificationCodes() {
        return verificationCodes;
    }

    public void setVerificationCodes(Set<VerificationCode> verificationCodes) {
        this.verificationCodes = verificationCodes;
    }

    public Set<Post> getLikedPosts() {
        return likedPosts;
    }

    public void setLikedPosts(Set<Post> likedPosts) {
        this.likedPosts = likedPosts;
    }
}
