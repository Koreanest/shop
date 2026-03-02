package com.hbk.legacy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TextBannerRepository extends JpaRepository<LegacyTextBanner, Long> {

    //“최대 sortOrder” 조회 메서드 추가
    @Query("select coalesce(max(t.sortOrder), 0) from TextBanner t")
    int findMaxSortOrder();
}
