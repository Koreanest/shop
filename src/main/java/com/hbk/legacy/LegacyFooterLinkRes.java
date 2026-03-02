package com.hbk.legacy;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LegacyFooterLinkRes {
    private Long id;
    private Long categoryId;
    private String label;
    private String url;
    private Integer sortOrder;
    private String visibleYn;

    public static LegacyFooterLinkRes from(LegacyFooterLink e){
        return LegacyFooterLinkRes.builder()
                .id(e.getId())
                .categoryId(e.getCategory().getId())
                .label(e.getLabel())
                .url(e.getUrl())
                .sortOrder(e.getSortOrder())
                .visibleYn(e.getVisibleYn())
                .build();
    }
}