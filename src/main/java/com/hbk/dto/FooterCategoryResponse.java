package com.hbk.dto;

import com.hbk.entity.FooterCategory;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FooterCategoryResponse {
    private Long id;
    private String title;
    private Integer sortOrder;
    private String visibleYn;

    public static FooterCategoryResponse  from(FooterCategory e){
        return FooterCategoryResponse .builder()
                .id(e.getId())
                .title(e.getTitle())
                .sortOrder(e.getSortOrder())
                .visibleYn(e.getVisibleYn())
                .build();
    }
}