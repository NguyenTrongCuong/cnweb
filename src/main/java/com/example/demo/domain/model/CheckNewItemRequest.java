package com.example.demo.domain.model;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CheckNewItemRequest {

    private Long last_id;
}
