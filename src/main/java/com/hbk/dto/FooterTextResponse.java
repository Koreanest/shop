package com.hbk.dto;

import com.hbk.entity.FooterText;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FooterTextResponse {
    private Long id;
    private String paragraph1;
    private String paragraph2;

    public static FooterTextResponse from(FooterText e){
        return FooterTextResponse.builder()
                .id(e.getId())
                .paragraph1(e.getParagraph1())
                .paragraph2(e.getParagraph2())
                .build();
    }
}