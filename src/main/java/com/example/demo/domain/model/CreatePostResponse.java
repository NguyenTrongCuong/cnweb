package com.example.demo.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public class CreatePostResponse {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
