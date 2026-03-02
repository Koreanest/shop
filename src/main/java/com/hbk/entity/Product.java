package com.hbk.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "products",
        indexes = {
                @Index(name = "idx_products_brand_id", columnList = "brand_id"),
                @Index(name = "idx_products_category_id", columnList = "category_id")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_products_slug", columnNames = "slug")
        }
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder

public class Product extends BaseTimeEntity {

    // products.id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    // products.brand_id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    // products.title
    @Column(name = "title", nullable = false, length = 100)
    private String title;

    // products.series
    @Column(name = "series", length = 80)
    private String series;

    // products.description
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    // products.price
    @Builder.Default
    @Column(name = "price", nullable = false)
    private Integer price = 0;

    // products.status
    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private ProductStatus status = ProductStatus.ACTIVE;


    // products.slug
    @Column(name = "slug", nullable = false, length = 150, unique = true)
    private String slug;

    // products.category_id
    // NOTE: 너 프로젝트에서 category가 꼭 필수
    // 필수로 갈 거면 nullable=false + optional=false로 바꾸면 됨.
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private NavMenu category;


    // products.image_url
    @Column(name = "image_url", nullable = false, length = 300)
    private String imageUrl;

    // products.image_path
    @Column(name = "image_path", nullable = false, length = 300)
    private String imagePath;

    // ===== 기존 구조 연결 =====
    // (라켓 스펙 1:1로 갈 때)
    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private ProductSpec spec;

    // (옵션/재고 단위 - 기존 ProductSize를 SKU로 진화시키는 방향)
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Sku> sizes = new ArrayList<>();



}