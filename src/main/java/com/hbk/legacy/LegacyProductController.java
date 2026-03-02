package com.hbk.controller;

import com.hbk.legacy.LegacyProductResponseDTO;
import com.hbk.service.LegacyProductService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

//@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class LegacyProductController {

    private final LegacyProductService legacyProductService;

    @GetMapping
    public List<LegacyProductResponseDTO> list() {
        return legacyProductService.list();
    }

    @GetMapping("/{id}")
    public LegacyProductResponseDTO detail(@PathVariable Long id) {
        return legacyProductService.getById(id);
    }

    // 🔥 slug로 조회
    @GetMapping("/slug/{slug}")
    public LegacyProductResponseDTO getBySlug(@PathVariable String slug) {
        return legacyProductService.getBySlug(slug);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public LegacyProductResponseDTO create(
            @RequestParam @NotBlank String title,
            @RequestParam(required = false) String desc,
            @RequestParam @NotNull Integer price,
            @RequestParam @NotNull Long categoryId,

            // ✅ sizes/specs (JSON 문자열)
            @RequestParam @NotBlank String sizes,
            @RequestParam @NotBlank String specs,

            @RequestPart("image") MultipartFile image
    ) throws Exception {
        return legacyProductService.create(title, desc, price, categoryId, sizes, specs, image);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public LegacyProductResponseDTO update(
            @PathVariable Long id,
            @RequestParam @NotBlank String title,
            @RequestParam(required = false) String desc,
            @RequestParam @NotNull Integer price,
            @RequestParam(required = false) Long categoryId,

            // ✅ sizes/specs (JSON 문자열)
            @RequestParam @NotBlank String sizes,
            @RequestParam @NotBlank String specs,

            @RequestPart(value = "image", required = false) MultipartFile image
    ) throws Exception {
        return legacyProductService.update(id, title, desc, price, categoryId, sizes, specs, image);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        legacyProductService.delete(id);
    }
}