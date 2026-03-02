package com.hbk.legacy;

import lombok.*;

import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class LegacyProductUpsertRequest {
    private String title;
    private String desc;
    private Integer price;
    private Long categoryId;

    private List<LegacyProductSizeDTO> sizes;
    private List<LegacyProductSpecDTO> specs;
}