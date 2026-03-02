package com.hbk.legacy;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class LegacyNavMenuResponseDTO {
    private Long id, parentId;
    private String name, path, visibleYn;
    private Integer sortOrder, depth;

    private List<LegacyNavMenuResponseDTO> children = new ArrayList<>();

}
