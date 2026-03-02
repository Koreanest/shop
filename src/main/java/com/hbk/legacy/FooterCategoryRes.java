package com.hbk.legacy;

import com.hbk.legacy.FooterCategory;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FooterCategoryRes {
    private Long id;
    private String title;
    private Integer sortOrder;
    private String visibleYn;

    public static FooterCategoryRes from(FooterCategory e){
        return FooterCategoryRes.builder()
                .id(e.getId())
                .title(e.getTitle())
                .sortOrder(e.getSortOrder())
                .visibleYn(e.getVisibleYn())
                .build();
    }
}