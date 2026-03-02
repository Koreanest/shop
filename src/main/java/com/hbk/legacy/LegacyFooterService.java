package com.hbk.legacy;

import com.hbk.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LegacyFooterService {

    private final FooterCategoryRepository categoryRepo;
    private final LegacyFooterLinkRepository linkRepo;
    private final FooterTextRepository textRepo;

    // -------- Category --------
    @Transactional(readOnly = true)
    public List<FooterCategoryRes> categories() {
        return categoryRepo.findAllByOrderBySortOrderAscIdAsc().stream()
                .map(FooterCategoryRes::from)
                .toList();
    }

    @Transactional
    public FooterCategoryRes createCategory(FooterCategoryCreateReq req) {
        String title = req.getTitle() == null ? "" : req.getTitle().trim();
        if (title.isBlank()) throw new IllegalArgumentException("title is required");

        String visibleYn = (req.getVisibleYn() == null || req.getVisibleYn().isBlank())
                ? "Y" : req.getVisibleYn().trim().toUpperCase();
        if (!visibleYn.equals("Y") && !visibleYn.equals("N")) throw new IllegalArgumentException("visibleYn must be Y or N");

        FooterCategory saved = categoryRepo.save(FooterCategory.builder()
                .title(title)
                .sortOrder(req.getSortOrder())
                .visibleYn(visibleYn)
                .build());

        return FooterCategoryRes.from(saved);
    }

    @Transactional
    public void deleteCategory(long id) {
        // 카테고리 삭제 전 링크 먼저 삭제(간단 처리)
        linkRepo.findByCategoryIdOrderBySortOrderAscIdAsc(id).forEach(linkRepo::delete);
        categoryRepo.deleteById(id);
    }

    // -------- Link --------
    @Transactional(readOnly = true)
    public List<LegacyFooterLinkRes> linksByCategory(long categoryId) {
        return linkRepo.findByCategoryIdOrderBySortOrderAscIdAsc(categoryId).stream()
                .map(LegacyFooterLinkRes::from)
                .toList();
    }

    @Transactional
    public LegacyFooterLinkRes createLink(LegacyFooterLinkCreateReq req) {
        if (req.getCategoryId() == null) throw new IllegalArgumentException("categoryId is required");

        FooterCategory cat = categoryRepo.findById(req.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("category not found: " + req.getCategoryId()));

        String label = req.getLabel() == null ? "" : req.getLabel().trim();
        String url = req.getUrl() == null ? "" : req.getUrl().trim();
        if (label.isBlank()) throw new IllegalArgumentException("label is required");
        if (url.isBlank()) throw new IllegalArgumentException("url is required");

        String visibleYn = (req.getVisibleYn() == null || req.getVisibleYn().isBlank())
                ? "Y" : req.getVisibleYn().trim().toUpperCase();
        if (!visibleYn.equals("Y") && !visibleYn.equals("N")) throw new IllegalArgumentException("visibleYn must be Y or N");

        LegacyFooterLink saved = linkRepo.save(LegacyFooterLink.builder()
                .category(cat)
                .label(label)
                .url(url)
                .sortOrder(req.getSortOrder())
                .visibleYn(visibleYn)
                .build());

        return LegacyFooterLinkRes.from(saved);
    }

    @Transactional
    public void deleteLink(long id) {
        linkRepo.deleteById(id);
    }

    // -------- FooterText (항상 1개) --------
    @Transactional(readOnly = true)
    public FooterTextRes getText() {
        FooterText t = textRepo.findById(1L).orElse(FooterText.builder().id(1L).paragraph1("").paragraph2("").build());
        return FooterTextRes.from(t);
    }

    @Transactional
    public FooterTextRes upsertText(FooterTextUpsertReq req) {
        FooterText t = textRepo.findById(1L).orElse(FooterText.builder().id(1L).build());
        t.setParagraph1(req.getParagraph1() == null ? "" : req.getParagraph1());
        t.setParagraph2(req.getParagraph2() == null ? "" : req.getParagraph2());
        return FooterTextRes.from(textRepo.save(t));
    }
}