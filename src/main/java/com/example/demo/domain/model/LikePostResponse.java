package com.example.demo.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public class LikePostResponse {

    private Long like;

    public Long getLike() {
        return like;
    }

    public void setLike(Long like) {
        this.like = like;
    }
}
