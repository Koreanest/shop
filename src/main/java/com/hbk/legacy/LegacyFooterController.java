package com.hbk.legacy;

import com.hbk.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/footer")
public class LegacyFooterController {

    private final LegacyFooterService legacyFooterService;

    // 1차 카테고리
    @GetMapping("/categories")
    public List<FooterCategoryRes> categories() {
        return legacyFooterService.categories();
    }

    @PostMapping("/categories")
    public FooterCategoryRes createCategory(@RequestBody FooterCategoryCreateReq req) {
        return legacyFooterService.createCategory(req);
    }

    @DeleteMapping("/categories/{id}")
    public void deleteCategory(@PathVariable long id) {
        legacyFooterService.deleteCategory(id);
    }

    // 2차 링크
    @GetMapping("/categories/{categoryId}/links")
    public List<LegacyFooterLinkRes> links(@PathVariable long categoryId) {
        return legacyFooterService.linksByCategory(categoryId);
    }

    @PostMapping("/links")
    public LegacyFooterLinkRes createLink(@RequestBody LegacyFooterLinkCreateReq req) {
        return legacyFooterService.createLink(req);
    }

    @DeleteMapping("/links/{id}")
    public void deleteLink(@PathVariable long id) {
        legacyFooterService.deleteLink(id);
    }

    // 하단 문구(문단 2개)
    @GetMapping("/text")
    public FooterTextRes getText() {
        return legacyFooterService.getText();
    }

    @PutMapping("/text")
    public FooterTextRes upsertText(@RequestBody FooterTextUpsertReq req) {
        return legacyFooterService.upsertText(req);
    }
}