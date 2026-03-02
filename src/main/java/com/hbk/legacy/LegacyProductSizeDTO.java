package com.hbk.legacy;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class LegacyProductSizeDTO {
    private Integer size;
    private Integer stock;
}