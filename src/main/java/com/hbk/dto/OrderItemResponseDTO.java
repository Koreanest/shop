package com.hbk.dto;

import com.hbk.entity.OrderItem;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemResponseDTO {

    private Long orderItemId;
    private Long skuId;
    private String skuCode;
    private String size;
    private Long productId;
    private String productTitle;
    private Integer unitPrice;
    private Integer quantity;
    private Integer lineTotal;

    public static OrderItemResponseDTO from(OrderItem item) {
        return OrderItemResponseDTO.builder()
                .orderItemId(item.getId())
                .skuId(item.getSku().getId())
                .skuCode(item.getSku().getSkuCode())
                .size(item.getGripSnapshot())
                .productId(item.getSku().getProduct().getId())
                .productTitle(item.getProductNameSnapshot())
                .unitPrice(item.getUnitPrice())
                .quantity(item.getQuantity())
                .lineTotal(item.getLineTotal())
                .build();
    }
}