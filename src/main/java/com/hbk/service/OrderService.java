package com.hbk.service;

import com.hbk.dto.OrderCreateRequestDTO;
import com.hbk.dto.OrderListItemResponseDTO;
import com.hbk.dto.OrderResponseDTO;
import com.hbk.entity.Cart;
import com.hbk.entity.CartItem;
import com.hbk.entity.Inventory;
import com.hbk.entity.Member;
import com.hbk.entity.Order;
import com.hbk.entity.OrderItem;
import com.hbk.entity.OrderStatus;
import com.hbk.entity.Sku;
import com.hbk.repository.CartRepository;
import com.hbk.repository.MemberRepository;
import com.hbk.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;

    public OrderResponseDTO createOrderFromCart(Long memberId, OrderCreateRequestDTO request) {
        Member member = getMemberOrThrow(memberId);
        Cart cart = getCartOrThrow(memberId);
        validateCartNotEmpty(cart);

        Order order = createOrderSkeleton(member, request);

        int totalPrice = 0;

        for (CartItem cartItem : cart.getItems()) {
            Sku sku = getSkuOrThrow(cartItem);
            validateSkuActive(sku);

            Inventory inventory = getInventoryOrThrow(sku);
            int quantity = getValidQuantity(cartItem.getQuantity());
            validateStock(inventory, quantity);

            int orderPrice = getOrderPriceOrThrow(sku);
            int lineAmount = orderPrice * quantity;
            String productNameSnapshot = getProductNameSnapshotOrThrow(cartItem);

            OrderItem orderItem = createOrderItem(
                    sku,
                    productNameSnapshot,
                    orderPrice,
                    quantity,
                    lineAmount
            );
            order.addItem(orderItem);

            decreaseStock(inventory, quantity);
            totalPrice += lineAmount;
        }

        order.setTotalPrice(totalPrice);

        Order savedOrder = orderRepository.save(order);

        clearCart(cart);

        return OrderResponseDTO.from(savedOrder);
    }

    @Transactional(readOnly = true)
    public List<OrderListItemResponseDTO> getMyOrders(Long memberId) {
        validateMemberExists(memberId);

        return orderRepository.findByMember_IdOrderByCreatedAtDesc(memberId)
                .stream()
                .map(OrderListItemResponseDTO::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public OrderResponseDTO getMyOrderDetail(Long memberId, Long orderId) {
        validateMemberExists(memberId);
        Order order = getOwnedOrder(memberId, orderId);
        return OrderResponseDTO.from(order);
    }

    public OrderResponseDTO cancelMyOrder(Long memberId, Long orderId) {
        validateMemberExists(memberId);
        Order order = getOwnedOrder(memberId, orderId);

        validateTransition(order, OrderStatus.CANCELED, OrderStatus.PENDING, OrderStatus.PAID);

        restoreStock(order);
        order.setStatus(OrderStatus.CANCELED);

        return OrderResponseDTO.from(order);
    }

    public OrderResponseDTO markPaid(Long orderId) {
        Order order = getOrderOrThrow(orderId);
        validateTransition(order, OrderStatus.PAID, OrderStatus.PENDING);
        order.setStatus(OrderStatus.PAID);
        return OrderResponseDTO.from(order);
    }

    public OrderResponseDTO startPreparing(Long orderId) {
        Order order = getOrderOrThrow(orderId);
        validateTransition(order, OrderStatus.PREPARING, OrderStatus.PAID);
        order.setStatus(OrderStatus.PREPARING);
        return OrderResponseDTO.from(order);
    }

    public OrderResponseDTO shipOrder(Long orderId) {
        Order order = getOrderOrThrow(orderId);
        validateTransition(order, OrderStatus.SHIPPED, OrderStatus.PREPARING);
        order.setStatus(OrderStatus.SHIPPED);
        return OrderResponseDTO.from(order);
    }

    public OrderResponseDTO completeOrder(Long orderId) {
        Order order = getOrderOrThrow(orderId);
        validateTransition(order, OrderStatus.COMPLETED, OrderStatus.SHIPPED);
        order.setStatus(OrderStatus.COMPLETED);
        return OrderResponseDTO.from(order);
    }

    private Member getMemberOrThrow(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "회원이 존재하지 않습니다. id=" + memberId
                ));
    }

    private void validateMemberExists(Long memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "회원이 존재하지 않습니다. id=" + memberId);
        }
    }

    private Cart getCartOrThrow(Long memberId) {
        return cartRepository.findByMember_Id(memberId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "장바구니가 없습니다."
                ));
    }

    private void validateCartNotEmpty(Cart cart) {
        if (cart.getItems() == null || cart.getItems().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "주문할 상품이 없습니다.");
        }
    }

    private Order createOrderSkeleton(Member member, OrderCreateRequestDTO request) {
        return Order.builder()
                .member(member)
                .orderNo(generateUniqueOrderNo())
                .status(OrderStatus.PENDING)
                .totalPrice(0)
                .receiverName(request.getReceiverName())
                .receiverPhone(request.getReceiverPhone())
                .zip(request.getZip())
                .address1(request.getAddress1())
                .address2(request.getAddress2())
                .memo(request.getMemo())
                .build();
    }

    private OrderItem createOrderItem(
            Sku sku,
            String productNameSnapshot,
            int orderPrice,
            int quantity,
            int lineAmount
    ) {
        return OrderItem.builder()
                .sku(sku)
                .productNameSnapshot(productNameSnapshot)
                .orderPrice(orderPrice)
                .quantity(quantity)
                .lineAmount(lineAmount)
                .build();
    }

    private String getProductNameSnapshotOrThrow(CartItem cartItem) {
        if (cartItem.getSku() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "SKU 정보가 없는 장바구니 항목입니다.");
        }

        if (cartItem.getSku().getProduct() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "상품 정보가 없는 장바구니 항목입니다.");
        }

        String title = cartItem.getSku().getProduct().getTitle();
        if (title == null || title.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "상품명이 비어 있습니다.");
        }

        return title;
    }

    private Sku getSkuOrThrow(CartItem cartItem) {
        if (cartItem.getSku() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "SKU 정보가 없는 장바구니 항목입니다.");
        }
        return cartItem.getSku();
    }

    private int getOrderPriceOrThrow(Sku sku) {
        if (sku.getPrice() == null || sku.getPrice() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "유효하지 않은 가격의 SKU입니다. skuId=" + sku.getId());
        }
        return sku.getPrice();
    }

    private int getValidQuantity(Integer quantity) {
        if (quantity == null || quantity < 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수량은 1 이상이어야 합니다.");
        }
        return quantity;
    }

    private Inventory getInventoryOrThrow(Sku sku) {
        if (sku.getInventory() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "재고 정보가 없는 SKU입니다. skuId=" + sku.getId());
        }
        return sku.getInventory();
    }

    private void validateStock(Inventory inventory, int requestedQty) {
        if (inventory.getStockQty() == null || requestedQty > inventory.getStockQty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "재고를 초과했습니다.");
        }
    }

    private void validateSkuActive(Sku sku) {
        if (sku.getIsActive() != null && !sku.getIsActive()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비활성 SKU는 주문할 수 없습니다.");
        }
    }

    private void decreaseStock(Inventory inventory, int quantity) {
        inventory.setStockQty(inventory.getStockQty() - quantity);
    }

    private void restoreStock(Order order) {
        if (order.getItems() == null) {
            return;
        }

        for (OrderItem item : order.getItems()) {
            if (item.getSku() == null) {
                continue;
            }

            Inventory inventory = getInventoryOrThrow(item.getSku());
            int currentStock = inventory.getStockQty() == null ? 0 : inventory.getStockQty();
            int restoreQty = item.getQuantity() == null ? 0 : item.getQuantity();

            inventory.setStockQty(currentStock + restoreQty);
        }
    }

    private void clearCart(Cart cart) {
        cart.clearItems();
    }

    private Order getOrderOrThrow(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "주문을 찾을 수 없습니다. id=" + orderId
                ));
    }

    private Order getOwnedOrder(Long memberId, Long orderId) {
        Order order = getOrderOrThrow(orderId);

        if (order.getMember() == null || order.getMember().getId() == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "본인 주문만 조회할 수 있습니다.");
        }

        if (!order.getMember().getId().equals(memberId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "본인 주문만 조회할 수 있습니다.");
        }

        return order;
    }

    private void validateTransition(Order order, OrderStatus targetStatus, OrderStatus... allowedCurrentStatuses) {
        OrderStatus currentStatus = order.getStatus();

        for (OrderStatus allowed : allowedCurrentStatuses) {
            if (allowed == currentStatus) {
                return;
            }
        }

        throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "주문 상태를 " + currentStatus + " 에서 " + targetStatus + " 로 변경할 수 없습니다."
        );
    }

    private String generateUniqueOrderNo() {
        String base = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        for (int i = 0; i < 10; i++) {
            String candidate = base + String.format("%03d", ThreadLocalRandom.current().nextInt(1000));
            if (orderRepository.findByOrderNo(candidate).isEmpty()) {
                return candidate;
            }
        }

        return base + System.nanoTime();
    }
}