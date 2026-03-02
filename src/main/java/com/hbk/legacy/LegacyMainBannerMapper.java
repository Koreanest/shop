package com.hbk.legacy;

public class LegacyMainBannerMapper {
    public static LegacyMainBannerResponse toResponse(LegacyMainBanner b) {
        if (b == null) return null;
        return LegacyMainBannerResponse.builder()
                .id(b.getId())
                .imageUrl(b.getImageUrl())
                .linkUrl(b.getLinkUrl())
                .build();
    }
}