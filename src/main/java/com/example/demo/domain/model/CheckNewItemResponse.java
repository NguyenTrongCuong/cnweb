package com.example.demo.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CheckNewItemResponse {

    private String new_items;

}
