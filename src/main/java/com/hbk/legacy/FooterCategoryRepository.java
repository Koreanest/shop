package com.hbk.legacy;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FooterCategoryRepository extends JpaRepository<FooterCategory, Long> {
    List<FooterCategory> findAllByOrderBySortOrderAscIdAsc();
}