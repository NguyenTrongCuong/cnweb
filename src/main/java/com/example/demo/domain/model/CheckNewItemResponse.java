package com.example.demo.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class CheckNewItemResponse {

    private Long id;

    private String description;

    private String status;

    private Long like;

    private List<String> images;

    private String video;

    private boolean is_liked;

    private Poster author;

    private List<CommentInfo> comments;

}
