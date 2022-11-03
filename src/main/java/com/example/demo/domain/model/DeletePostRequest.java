package com.example.demo.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class DeletePostRequest {

    private Long id;

    public DeletePostRequest() {}

    public DeletePostRequest(Long id) {
        this.id = id;
    }
}
