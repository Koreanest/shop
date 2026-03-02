package com.hbk.legacy;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LegacyFooterLinkRepository extends JpaRepository<LegacyFooterLink, Long> {
    List<LegacyFooterLink> findByCategoryIdOrderBySortOrderAscIdAsc(Long categoryId);
    List<LegacyFooterLink> findAllByOrderBySortOrderAscIdAsc();
}