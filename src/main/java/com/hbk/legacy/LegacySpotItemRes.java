package com.hbk.legacy;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LegacySpotItemRes {

    private Long id;
    private String title;
    private String imageUrl;
    private String linkUrl;
    private Integer sortOrder;
    private String visibleYn;

    public static LegacySpotItemRes from(LegacySpotItem e) {
        return LegacySpotItemRes.builder()
                .id(e.getId())
                .title(e.getTitle())
                .imageUrl(e.getImageUrl())
                .linkUrl(e.getLinkUrl())
                .sortOrder(e.getSortOrder())
                .visibleYn(e.getVisibleYn())
                .build();
    }
}