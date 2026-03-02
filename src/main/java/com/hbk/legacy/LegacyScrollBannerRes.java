// ✅ ScrollBannerRes.java
package com.hbk.legacy;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LegacyScrollBannerRes {

    private Long id;
    private String title;
    private String imageUrl;

    private String linkUrl;

    private String buttonText;
    private String buttonLinkUrl;

    private Integer sortOrder;
    private String visibleYn;

    public static LegacyScrollBannerRes from(LegacyScrollBanner e) {
        return LegacyScrollBannerRes.builder()
                .id(e.getId())
                .title(e.getTitle())
                .imageUrl(e.getImageUrl())
                .linkUrl(e.getLinkUrl())
                .buttonText(e.getButtonText())
                .buttonLinkUrl(e.getButtonLinkUrl())
                .sortOrder(e.getSortOrder())
                .visibleYn(e.getVisibleYn())
                .build();
    }
}