package com.hbk.legacy;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class LegacyProductSpecDTO {
    private String label;
    private String value;
}