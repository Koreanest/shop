package com.hbk.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sku_id", nullable = false)
    private Sku sku;

    @Column(name = "product_name_snapshot", nullable = false, length = 120)
    private String productNameSnapshot;

    @Column(name = "brand_name_snapshot", nullable = false, length = 50)
    private String brandNameSnapshot;

    @Column(name = "grip_snapshot", nullable = false, length = 4)
    private String gripSnapshot;

    @Column(name = "unit_price", nullable = false)
    private Integer unitPrice;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "line_total", nullable = false)
    private Integer lineTotal;
}