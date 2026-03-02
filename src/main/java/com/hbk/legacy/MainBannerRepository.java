package com.hbk.legacy;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MainBannerRepository extends JpaRepository<LegacyMainBanner, Long> {
    Optional<LegacyMainBanner> findTopByOrderByIdDesc();
}