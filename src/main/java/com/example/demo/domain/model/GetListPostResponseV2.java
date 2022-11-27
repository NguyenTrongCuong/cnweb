package com.example.demo.domain.model;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetListPostResponseV2 {

    private List<GetListPostsResponse> posts;

    private String new_items;

    private String last_id;

    private String in_campaign;

    private String campaign_id;

}
