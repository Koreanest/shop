package com.hbk.legacy;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LegacyMainBannerResponse {
    private Long id;
    private String imageUrl;
    private String linkUrl;
}
