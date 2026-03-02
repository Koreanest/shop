package com.hbk.legacy;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/scroll-banners")
public class ScrollBannerController {

    private final LegacyScrollBannerService legacyScrollBannerService;

    /** ✅ 목록 */
    @GetMapping
    public List<LegacyScrollBannerRes> list() {
        return legacyScrollBannerService.list();
    }

    /** ✅ 생성 (multipart/form-data) */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public LegacyScrollBannerRes create(@ModelAttribute LegacyScrollBannerCreateReq req) {
        return legacyScrollBannerService.create(req);
    }

    /** ✅ 삭제 */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        legacyScrollBannerService.delete(id);
    }
}