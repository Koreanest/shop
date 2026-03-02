package com.hbk.legacy;

import com.hbk.entity.Product;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LegacyProductResponseDTO {

    private Long id;
    private String title;
    private String desc;
    private Integer price;

    private String imageUrl;
    private String slug;

    private Long categoryId;
    private String categoryName;

    private List<LegacyProductSizeDTO> sizes;
    private List<LegacyProductSpecDTO> specs;

    // 🔥 반드시 있어야 함
    public static LegacyProductResponseDTO from(Product e) {
        return LegacyProductResponseDTO.builder()
                .id(e.getId())
                .title(e.getTitle())
                .desc(e.getDescription())
                .price(e.getPrice())
                .imageUrl(e.getImageUrl())
                .slug(e.getSlug())
                .categoryId(e.getCategory().getId())
                .categoryName(e.getCategory().getName())

                // ✅ 사이즈 매핑
                .sizes(
                        e.getSizes().stream()
                                .map(s -> new LegacyProductSizeDTO(
                                        s.getSize(),
                                        s.getStock()
                                ))
                                .toList()
                )

                // ✅ 상품정보고시 매핑
                .specs(
                        e.getSpecs().stream()
                                .map(s -> new LegacyProductSpecDTO(
                                        s.getLabel(),
                                        s.getValue()
                                ))
                                .toList()
                )
                .build();
    }
}