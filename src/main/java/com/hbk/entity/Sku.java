package com.hbk.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "skus")

@Getter //Getter 자동 생성
@Setter //Setter 자동 생성
@NoArgsConstructor //기본 생성자 생성
@AllArgsConstructor //전체 필드 생성자 생성
@Builder(toBuilder = true)
//Builder 패턴 지원 toBuilder() 가능 (기존 객체 복사 수정 가능)
public class Sku {

    @Id //pk
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    /*
여러 ProductSize는 하나의 Product에 속한다
LAZY = 필요할 때만 Product 조회
optional = false → null 불가
외래키 컬럼 이름은 product_id
    */


    //해당사이즈 재고 수량
    @Column(nullable = false)
    private Integer price;

    @Column(name = "sku_code", nullable = false, length = 80, unique = true)
    private String skuCode;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "grip_size", nullable = false, length = 4)
    private String gripSize;

    @OneToOne(mappedBy = "sku", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Inventory inventory;
}