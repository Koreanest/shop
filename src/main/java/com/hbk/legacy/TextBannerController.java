package com.hbk.legacy;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/text-banners")
public class TextBannerController {

    private final LegacyTextBannerService legacyTextBannerService;

    @GetMapping
    public List<LegacyTextBannerRes> list() {
        return legacyTextBannerService.list();
    }

    // ✅ 생성 (multipart/form-data)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public LegacyTextBannerRes create(@ModelAttribute LegacyTextBannerCreateReq req) {
        return legacyTextBannerService.create(req);
    }

    // ✅ 삭제
    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        legacyTextBannerService.delete(id);
    }
}