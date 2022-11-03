package com.example.demo.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public class LikePostRequest {

    // 1. get list posts => last_id + count
    // 2. check new items

    private Long id;

    public LikePostRequest() {}

    public LikePostRequest(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
