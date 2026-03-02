package com.hbk.legacy;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class LegacyNavMenuRequestDTO {

    private String name, path, visibleYn;
    private Long parentId;
    private Integer sortOrder;

}
