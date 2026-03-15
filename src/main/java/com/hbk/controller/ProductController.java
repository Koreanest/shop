package com.hbk.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hbk.dto.PageResponseDTO;
import com.hbk.dto.ProductListItemDTO;
import com.hbk.dto.ProductResponseDTO;
import com.hbk.dto.ProductSearchRequestDTO;
import com.hbk.dto.ProductSizeDTO;
import com.hbk.dto.ProductSpecDTO;
import com.hbk.dto.product.request.ProductUpsertMultipartRequest;
import com.hbk.service.ProductQueryService;
import com.hbk.service.ProductService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class ProductController {

    private final ProductService productService;
    private final ProductQueryService productQueryService;
    private final ObjectMapper objectMapper;

    /**
     * 상품 목록 조회
     * 예:
     * GET /api/products?page=0&size=12&sort=latest
     * GET /api/products?brandId=3&categoryId=100
     * GET /api/products?keyword=vcore&minPrice=200000&maxPrice=300000
     */
    @GetMapping
    public PageResponseDTO<ProductListItemDTO> list(
            @RequestParam(required = false) Long brandId,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice,
            @RequestParam(required = false) String status,
            @RequestParam(required = false, defaultValue = "latest") String sort,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "12") Integer size
    ) {
        ProductSearchRequestDTO request = ProductSearchRequestDTO.builder()
                .brandId(brandId)
                .categoryId(categoryId)
                .keyword(keyword)
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .status(status)
                .sort(sort)
                .page(page)
                .size(size)
                .build();

        return productQueryService.search(request);
    }

    /**
     * 상품 상세 조회
     */
    @GetMapping("/{id}")
    public ProductResponseDTO detail(@PathVariable Long id) {
        return productService.getById(id);
    }

    /**
     * slug 기반 상품 상세 조회
     */
    @GetMapping("/slug/{slug}")
    public ProductResponseDTO getBySlug(@PathVariable String slug) {
        return productService.getBySlug(slug);
    }

    /**
     * 기존 상품 등록 방식
     * multipart/form-data + 개별 RequestParam
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ProductResponseDTO create(
            @RequestParam @NotBlank String title,
            @RequestParam(required = false) String description,
            @RequestParam @NotNull Integer price,
            @RequestParam @NotNull Long brandId,
            @RequestParam @NotNull Long categoryId,
            @RequestParam @NotBlank String sizes,
            @RequestParam @NotBlank String spec,
            @RequestPart("image") MultipartFile image
    ) throws Exception {
        return productService.create(
                title,
                description,
                price,
                brandId,
                categoryId,
                sizes,
                spec,
                image
        );
    }

    /**
     * 기존 상품 수정 방식
     * multipart/form-data + 개별 RequestParam
     */
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ProductResponseDTO update(
            @PathVariable Long id,
            @RequestParam @NotBlank String title,
            @RequestParam(required = false) String description,
            @RequestParam @NotNull Integer price,
            @RequestParam(required = false) Long brandId,
            @RequestParam(required = false) Long categoryId,
            @RequestParam @NotBlank String sizes,
            @RequestParam @NotBlank String spec,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) throws Exception {
        return productService.update(
                id,
                title,
                description,
                price,
                brandId,
                categoryId,
                sizes,
                spec,
                image
        );
    }

    /**
     * DTO 기반 multipart 상품 등록
     * - sizes/specs를 JSON 문자열로 받음
     * - ProductService.create(...) 시그니처에 맞게 다시 JSON 문자열로 직렬화해서 전달
     */
    @PostMapping(value = "/multipart", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ProductResponseDTO createProductMultipart(
            @ModelAttribute ProductUpsertMultipartRequest request
    ) {
        try {
            validateMultipartRequest(request);

            List<ProductSizeDTO> sizes = parseSizes(request.getSizes());
            List<ProductSpecDTO> specs = parseSpecs(request.getSpecs());

            String sizesJson = objectMapper.writeValueAsString(sizes);

            if (specs.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "specs는 1개 이상 필요합니다.");
            }
            if (specs.size() > 1) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "현재 specs는 1개만 지원합니다.");
            }

            String specJson = objectMapper.writeValueAsString(specs.get(0));

            return productService.create(
                    request.getTitle(),
                    request.getDescription(),
                    request.getPrice(),
                    request.getBrandId(),
                    request.getCategoryId(),
                    sizesJson,
                    specJson,
                    request.getMainImage()
            );
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "상품 생성 실패");
        }
    }

    /**
     * 상품 삭제
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    private void validateMultipartRequest(ProductUpsertMultipartRequest request) {
        if (request.getTitle() == null || request.getTitle().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "상품명은 필수입니다.");
        }
        if (request.getPrice() == null || request.getPrice() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "가격은 0보다 커야 합니다.");
        }
        if (request.getBrandId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "브랜드는 필수입니다.");
        }
        if (request.getCategoryId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "카테고리는 필수입니다.");
        }
        if (request.getMainImage() == null || request.getMainImage().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미지는 필수입니다.");
        }
    }

    private List<ProductSizeDTO> parseSizes(String json) {
        if (json == null || json.isBlank()) {
            return new ArrayList<>();
        }

        try {
            return objectMapper.readValue(
                    json,
                    new TypeReference<List<ProductSizeDTO>>() {}
            );
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "sizes JSON 형식이 올바르지 않습니다.");
        }
    }

    private List<ProductSpecDTO> parseSpecs(String json) {
        if (json == null || json.isBlank()) {
            return new ArrayList<>();
        }

        try {
            return objectMapper.readValue(
                    json,
                    new TypeReference<List<ProductSpecDTO>>() {}
            );
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "specs JSON 형식이 올바르지 않습니다.");
        }
    }
}