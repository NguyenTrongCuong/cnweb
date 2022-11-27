package com.example.demo.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public class CreateCommentResponse {

    private String id;
    private String comment;
    private String createdAt;
    private Poster poster;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Poster getPoster() {
        return poster;
    }

    public void setPoster(Poster poster) {
        this.poster = poster;
    }
}
