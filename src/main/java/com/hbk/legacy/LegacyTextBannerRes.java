package com.hbk.legacy;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LegacyTextBannerRes {

    private Long id;
    private String title;
    private String desc;
    private String imageUrl;
    private String linkUrl;
    private Integer sortOrder;
    private String visibleYn; // "Y" | "N"

    public static LegacyTextBannerRes from(LegacyTextBanner e) {
        return LegacyTextBannerRes.builder()
                .id(e.getId())
                .title(e.getTitle())
                .desc(e.getDesc())
                .imageUrl(e.getImageUrl())
                .linkUrl(e.getLinkUrl())
                .sortOrder(e.getSortOrder())
                .visibleYn(e.getVisibleYn())
                .build();
    }
}
