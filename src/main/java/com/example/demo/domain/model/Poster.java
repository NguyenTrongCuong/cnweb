package com.example.demo.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public class Poster {

    private String id;
    private String name;
    private String avatar;

    private String online;

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
