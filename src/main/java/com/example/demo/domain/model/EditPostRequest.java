package com.example.demo.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Builder
public class EditPostRequest {

    private Long id;

    private String status;

    private String described;

    private List<MultipartFile> images;

    private List<Long> image_del;

    private List<Integer> image_sort;

    private MultipartFile video;

    private String auto_accept;

    private String auto_block;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescribed() {
        return described;
    }

    public void setDescribed(String described) {
        this.described = described;
    }

    public List<MultipartFile> getImages() {
        return images;
    }

    public void setImages(List<MultipartFile> images) {
        this.images = images;
    }

    public List<Long> getImage_del() {
        return image_del;
    }

    public void setImage_del(List<Long> image_del) {
        this.image_del = image_del;
    }

    public List<Integer> getImage_sort() {
        return image_sort;
    }

    public void setImage_sort(List<Integer> image_sort) {
        this.image_sort = image_sort;
    }

    public MultipartFile getVideo() {
        return video;
    }

    public void setVideo(MultipartFile video) {
        this.video = video;
    }

    public String getAuto_accept() {
        return auto_accept;
    }

    public void setAuto_accept(String auto_accept) {
        this.auto_accept = auto_accept;
    }

    public String getAuto_block() {
        return auto_block;
    }

    public void setAuto_block(String auto_block) {
        this.auto_block = auto_block;
    }
}
