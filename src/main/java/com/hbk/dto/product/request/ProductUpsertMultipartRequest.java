package com.hbk.dto.product.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductUpsertMultipartRequest {

    @NotBlank(message = "상품명은 필수입니다.")
    @Size(max = 120, message = "상품명은 120자를 초과할 수 없습니다.")
    private String title;

    @Size(max = 2000, message = "설명은 2000자를 초과할 수 없습니다.")
    private String description;

    @NotNull(message = "대표 가격은 필수입니다.")
    @Positive(message = "대표 가격은 0보다 커야 합니다.")
    private Integer price;

    @NotNull(message = "카테고리 ID는 필수입니다.")
    private Long categoryId;

    private Long brandId;

    /**
     * 프론트에서 FormData로 보내는 JSON 문자열
     * fd.append("sizes", JSON.stringify([...]))
     */
    private String sizes;

    /**
     * 프론트에서 FormData로 보내는 JSON 문자열
     * fd.append("specs", JSON.stringify([...]))
     */
    private String specs;

    /**
     * 대표 이미지
     */
    private MultipartFile mainImage;
}