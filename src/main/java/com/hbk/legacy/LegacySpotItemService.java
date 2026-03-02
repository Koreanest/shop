package com.hbk.legacy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LegacySpotItemService {

    private final SpotItemRepository spotItemRepository;
    private final LegacyFileStorageService legacyFileStorageService;

    /** ✅ 목록 조회 (sortOrder 오름차순) */
    @Transactional(readOnly = true)
    public List<LegacySpotItemRes> list() {
        // repo에 findAllByOrderBySortOrderAscIdAsc() 만들어뒀으면 그걸 써도 됨.
        return spotItemRepository.findAll().stream()
                .sorted(Comparator.comparingInt(e -> e.getSortOrder() == null ? 0 : e.getSortOrder()))
                .map(LegacySpotItemRes::from)
                .toList();
    }

    /** ✅ 생성 (multipart/form-data) */
    @Transactional
    public LegacySpotItemRes create(LegacySpotItemCreateReq req) {
        String title = req.getTitle() == null ? "" : req.getTitle().trim();
        if (title.isBlank()) throw new IllegalArgumentException("title is required");

        // ✅ visibleYn 기본값
        String visibleYn = (req.getVisibleYn() == null || req.getVisibleYn().isBlank())
                ? "Y"
                : req.getVisibleYn().trim().toUpperCase();

        if (!visibleYn.equals("Y") && !visibleYn.equals("N")) {
            throw new IllegalArgumentException("visibleYn must be Y or N");
        }

        // ✅ sortOrder 기본값: max + 1
        Integer sortOrder = req.getSortOrder();
        if (sortOrder == null) {
            int max = spotItemRepository.findMaxSortOrder();
            sortOrder = max + 1;
        }

        String linkUrl = req.getLinkUrl() == null ? null : req.getLinkUrl().trim();

        // ✅ 이미지 업로드(선택)
        String imageUrl = null;
        try {
            imageUrl = legacyFileStorageService.saveTextBannerImage(req.getImage());
        } catch (Exception e) {
            throw new RuntimeException("image upload failed", e);
        }

        LegacySpotItem saved = spotItemRepository.save(
                LegacySpotItem.builder()
                        .title(title)
                        .imageUrl(imageUrl)
                        .linkUrl(linkUrl)
                        .sortOrder(sortOrder)
                        .visibleYn(visibleYn)
                        .build()
        );

        return LegacySpotItemRes.from(saved);
    }

    /** ✅ 삭제 (이미지 파일도 같이 삭제 시도) */
    @Transactional
    public void delete(long id) {
        LegacySpotItem entity = spotItemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("spot item not found: " + id));

        legacyFileStorageService.deleteByRelativeUrl(entity.getImageUrl());
        spotItemRepository.delete(entity);
    }
}