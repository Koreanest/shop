package com.hbk.service;

import com.hbk.dto.FooterCategoryCreateRequest;
import com.hbk.dto.FooterCategoryResponse;
import com.hbk.dto.FooterLinkCreateRequest;
import com.hbk.dto.FooterLinkResponse;
import com.hbk.dto.FooterTextResponse;
import com.hbk.dto.FooterTextUpsertRequest;
import com.hbk.entity.FooterCategory;
import com.hbk.entity.FooterLink;
import com.hbk.entity.FooterText;
import com.hbk.repository.FooterCategoryRepository;
import com.hbk.repository.FooterLinkRepository;
import com.hbk.repository.FooterTextRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FooterService {

    private static final Long FOOTER_TEXT_ID = 1L;

    private final FooterCategoryRepository categoryRepo;
    private final FooterLinkRepository linkRepo;
    private final FooterTextRepository textRepo;

    // -------- Category --------
    @Transactional(readOnly = true)
    public List<FooterCategoryResponse> categories() {
        return categoryRepo.findAllByOrderBySortOrderAscIdAsc().stream()
                .map(FooterCategoryResponse::from)
                .toList();
    }

    @Transactional
    public FooterCategoryResponse createCategory(FooterCategoryCreateRequest req) {
        String title = req == null || req.getTitle() == null ? "" : req.getTitle().trim();
        if (title.isBlank()) {
            throw new IllegalArgumentException("title is required");
        }

        String visibleYn = normalizeVisibleYn(req.getVisibleYn());
        Integer sortOrder = normalizeSortOrder(req.getSortOrder());

        FooterCategory saved = categoryRepo.save(
                FooterCategory.builder()
                        .title(title)
                        .sortOrder(sortOrder)
                        .visibleYn(visibleYn)
                        .build()
        );

        return FooterCategoryResponse.from(saved);
    }

    @Transactional
    public void deleteCategory(long id) {
        FooterCategory category = categoryRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("category not found: " + id));

        linkRepo.deleteByCategoryId(category.getId());
        categoryRepo.delete(category);
    }

    // -------- Link --------
    @Transactional(readOnly = true)
    public List<FooterLinkResponse> linksByCategory(long categoryId) {
        if (!categoryRepo.existsById(categoryId)) {
            throw new IllegalArgumentException("category not found: " + categoryId);
        }

        return linkRepo.findByCategoryIdOrderBySortOrderAscIdAsc(categoryId).stream()
                .map(FooterLinkResponse::from)
                .toList();
    }

    @Transactional
    public FooterLinkResponse createLink(FooterLinkCreateRequest req) {
        if (req == null || req.getCategoryId() == null) {
            throw new IllegalArgumentException("categoryId is required");
        }

        FooterCategory category = categoryRepo.findById(req.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("category not found: " + req.getCategoryId()));

        String label = req.getLabel() == null ? "" : req.getLabel().trim();
        String url = req.getUrl() == null ? "" : req.getUrl().trim();

        if (label.isBlank()) {
            throw new IllegalArgumentException("label is required");
        }
        if (url.isBlank()) {
            throw new IllegalArgumentException("url is required");
        }

        String visibleYn = normalizeVisibleYn(req.getVisibleYn());
        Integer sortOrder = normalizeSortOrder(req.getSortOrder());

        FooterLink saved = linkRepo.save(
                FooterLink.builder()
                        .category(category)
                        .label(label)
                        .url(url)
                        .sortOrder(sortOrder)
                        .visibleYn(visibleYn)
                        .build()
        );

        return FooterLinkResponse.from(saved);
    }

    @Transactional
    public void deleteLink(long id) {
        if (!linkRepo.existsById(id)) {
            throw new IllegalArgumentException("footer link not found: " + id);
        }
        linkRepo.deleteById(id);
    }

    // -------- FooterText (항상 1개) --------
    @Transactional(readOnly = true)
    public FooterTextResponse getText() {
        FooterText text = textRepo.findById(FOOTER_TEXT_ID)
                .orElse(
                        FooterText.builder()
                                .id(FOOTER_TEXT_ID)
                                .paragraph1("")
                                .paragraph2("")
                                .build()
                );

        return FooterTextResponse.from(text);
    }

    @Transactional
    public FooterTextResponse upsertText(FooterTextUpsertRequest req) {
        FooterText text = textRepo.findById(FOOTER_TEXT_ID)
                .orElse(FooterText.builder().id(FOOTER_TEXT_ID).build());

        text.setParagraph1(req == null || req.getParagraph1() == null ? "" : req.getParagraph1());
        text.setParagraph2(req == null || req.getParagraph2() == null ? "" : req.getParagraph2());

        FooterText saved = textRepo.save(text);
        return FooterTextResponse.from(saved);
    }

    private String normalizeVisibleYn(String visibleYn) {
        if (visibleYn == null || visibleYn.isBlank()) {
            return "Y";
        }

        String normalized = visibleYn.trim().toUpperCase();
        if (!normalized.equals("Y") && !normalized.equals("N")) {
            throw new IllegalArgumentException("visibleYn must be Y or N");
        }

        return normalized;
    }

    private Integer normalizeSortOrder(Integer sortOrder) {
        return sortOrder == null ? 0 : sortOrder;
    }
}