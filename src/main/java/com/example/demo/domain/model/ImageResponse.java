package com.example.demo.domain.model;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageResponse {

    private String id;

    private String url;

}
