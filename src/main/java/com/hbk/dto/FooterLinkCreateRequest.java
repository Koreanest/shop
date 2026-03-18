package com.hbk.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FooterLinkCreateRequest {
    private Long categoryId; // 어떤 1차 아래에 넣을지
    private String label;
    private String url;
    private Integer sortOrder;
    private String visibleYn; // Y/N
}