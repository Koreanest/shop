package com.hbk.controller;

import com.hbk.dto.OrderCreateRequestDTO;
import com.hbk.dto.OrderListItemResponseDTO;
import com.hbk.dto.OrderResponseDTO;
import com.hbk.service.OrderService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public OrderResponseDTO createOrder(
            @Valid @RequestBody OrderCreateRequestDTO request,
            HttpSession session
    ) {
        Long memberId = getLoginMemberId(session);
        return orderService.createOrderFromCart(memberId, request);
    }

    @GetMapping
    public List<OrderListItemResponseDTO> getMyOrders(HttpSession session) {
        Long memberId = getLoginMemberId(session);
        return orderService.getMyOrders(memberId);
    }

    @GetMapping("/{id}")
    public OrderResponseDTO getMyOrderDetail(
            @PathVariable Long id,
            HttpSession session
    ) {
        Long memberId = getLoginMemberId(session);
        return orderService.getMyOrderDetail(memberId, id);
    }
    @PostMapping("/{id}/pay")
    public OrderResponseDTO markPaid(@PathVariable Long id) {
        return orderService.markPaid(id);
    }
    @PostMapping("/{id}/prepare")
    public OrderResponseDTO startPreparing(@PathVariable Long id) {
        return orderService.startPreparing(id);
    }
    @PostMapping("/{id}/ship")
    public OrderResponseDTO shipOrder(@PathVariable Long id) {
        return orderService.shipOrder(id);
    }

    @PostMapping("/{id}/complete")
    public OrderResponseDTO completeOrder(@PathVariable Long id) {
        return orderService.completeOrder(id);
    }
    @PostMapping("/{id}/cancel")
    public OrderResponseDTO cancelMyOrder(
            @PathVariable Long id,
            HttpSession session
    ) {
        Long memberId = getLoginMemberId(session);
        return orderService.cancelMyOrder(memberId, id);
    }

    private Long getLoginMemberId(HttpSession session) {
        Object value = session.getAttribute("LOGIN_MEMBER_ID");

        if (value == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        }

        if (value instanceof Long memberId) {
            return memberId;
        }

        if (value instanceof Integer memberId) {
            return memberId.longValue();
        }

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인 정보가 올바르지 않습니다.");
    }
}