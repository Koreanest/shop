package com.hbk.dto.product.request;

import com.hbk.dto.ProductSizeDTO;
import com.hbk.dto.ProductSpecDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductUpsertRequest {

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

    @Valid
    @Builder.Default
    private List<ProductSizeDTO> sizes = new ArrayList<>();

    @Valid
    @Builder.Default
    private List<ProductSpecDTO> specs = new ArrayList<>();
}