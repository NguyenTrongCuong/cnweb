package com.example.demo.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
public class GetListPostsResponse {

    private String id;

    private String described;

    private String state;

    private String like;

    private List<ImageResponse> images;

    private String video;

    private String is_liked;

    private Poster author;

    private String comments;

    private String created;

    private String modified;

    private String is_blocked;

    private String can_edit;

    private String banned;

    private String can_comment;

    private String url;

    private List<String> messages;

    public String getIs_blocked() {
        return is_blocked;
    }

    public void setIs_blocked(String is_blocked) {
        this.is_blocked = is_blocked;
    }

    public String getCan_edit() {
        return can_edit;
    }

    public void setCan_edit(String can_edit) {
        this.can_edit = can_edit;
    }

    public String getBanned() {
        return banned;
    }

    public void setBanned(String banned) {
        this.banned = banned;
    }

    public String getCan_comment() {
        return can_comment;
    }

    public void setCan_comment(String can_comment) {
        this.can_comment = can_comment;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getDescribed() {
        return described;
    }

    public void setDescribed(String described) {
        this.described = described;
    }

    public String getLike() {
        return like;
    }

    public String getIs_liked() {
        return is_liked;
    }

    public void setIs_liked(String is_liked) {
        this.is_liked = is_liked;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<ImageResponse> getImages() {
        return images;
    }

    public void setImages(List<ImageResponse> images) {
        this.images = images;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public Poster getAuthor() {
        return author;
    }

    public void setAuthor(Poster author) {
        this.author = author;
    }
}
