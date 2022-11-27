package com.example.demo.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public class LikePostResponse {

    private String like;

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }
}
