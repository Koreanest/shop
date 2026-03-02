package com.hbk.legacy;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/spot-items")
public class SpotItemController {

    private final LegacySpotItemService legacySpotItemService;

    /** ✅ 목록 */
    @GetMapping
    public List<LegacySpotItemRes> list() {
        return legacySpotItemService.list();
    }

    /** ✅ 생성 (multipart/form-data) */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public LegacySpotItemRes create(@ModelAttribute LegacySpotItemCreateReq req) {
        return legacySpotItemService.create(req);
    }

    /** ✅ 삭제 */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        legacySpotItemService.delete(id);
    }
}