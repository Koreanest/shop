package com.hbk.dto;

import com.hbk.entity.Order;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderListItemResponseDTO {

    private Long orderId;
    private String orderNo;
    private String status;
    private Integer totalPrice;
    private String receiverName;
    private LocalDateTime createdAt;

    public static OrderListItemResponseDTO from(Order order) {
        return OrderListItemResponseDTO.builder()
                .orderId(order.getId())
                .orderNo(order.getOrderNo())
                .status(order.getStatus().name())
                .totalPrice(order.getTotalPrice())
                .receiverName(order.getReceiverName())
                .createdAt(order.getCreatedAt())
                .build();
    }
}