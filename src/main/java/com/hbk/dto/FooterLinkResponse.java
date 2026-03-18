package com.hbk.dto;

import com.hbk.entity.FooterLink;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FooterLinkResponse {
    private Long id;
    private Long categoryId;
    private String label;
    private String url;
    private Integer sortOrder;
    private String visibleYn;

    public static FooterLinkResponse from(FooterLink e){
        return FooterLinkResponse.builder()
                .id(e.getId())
                .categoryId(e.getCategory().getId())
                .label(e.getLabel())
                .url(e.getUrl())
                .sortOrder(e.getSortOrder())
                .visibleYn(e.getVisibleYn())
                .build();
    }
}