package com.hbk.service;

import com.hbk.dto.CartAddRequestDTO;
import com.hbk.dto.CartItemQuantityUpdateRequestDTO;
import com.hbk.dto.CartResponseDTO;
import com.hbk.entity.Cart;
import com.hbk.entity.CartItem;
import com.hbk.entity.Inventory;
import com.hbk.entity.Member;
import com.hbk.entity.Sku;
import com.hbk.repository.CartItemRepository;
import com.hbk.repository.CartRepository;
import com.hbk.repository.MemberRepository;
import com.hbk.repository.SkuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final MemberRepository memberRepository;
    private final SkuRepository skuRepository;

    @Transactional
    public CartResponseDTO getCart(Long memberId) {
        Cart cart = cartRepository.findByMember_Id(memberId)
                .orElseGet(() -> {
                    Member member = getMember(memberId);
                    Cart newCart = Cart.builder()
                            .member(member)
                            .build();
                    return cartRepository.save(newCart);
                });

        return CartResponseDTO.of(memberId, cart);
    }

    public CartResponseDTO addItem(Long memberId, CartAddRequestDTO request) {
        Member member = getMember(memberId);
        Cart cart = cartRepository.findByMember_Id(memberId)
                .orElseGet(() -> cartRepository.save(
                        Cart.builder()
                                .member(member)
                                .build()
                ));

        Sku sku = skuRepository.findById(request.getSkuId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "상품 옵션을 찾을 수 없습니다."
                ));

        validateSkuActive(sku);
        Inventory inventory = getInventoryOrThrow(sku);

        CartItem existingItem = cartItemRepository.findByCart_IdAndSku_Id(cart.getId(), sku.getId())
                .orElse(null);

        int currentQty = existingItem != null ? existingItem.getQuantity() : 0;
        int addQty = request.getQuantity();
        int totalQty = currentQty + addQty;

        validateStockForAdd(inventory, totalQty);

        if (existingItem != null) {
            existingItem.changeQuantity(totalQty);
        } else {
            CartItem newItem = CartItem.builder()
                    .sku(sku)
                    .quantity(addQty)
                    .build();
            cart.addItem(newItem);
        }

        return CartResponseDTO.of(memberId, cartRepository.save(cart));
    }

    public CartResponseDTO updateQuantity(Long memberId, Long cartItemId, CartItemQuantityUpdateRequestDTO request) {
        Cart cart = cartRepository.findByMember_Id(memberId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "장바구니가 없습니다."));

        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "장바구니 상품을 찾을 수 없습니다."
                ));

        validateCartOwnership(cart, item);

        Sku sku = item.getSku();
        validateSkuActive(sku);

        Inventory inventory = getInventoryOrThrow(sku);
        validateStockForUpdate(inventory, request.getQuantity());

        item.changeQuantity(request.getQuantity());

        return CartResponseDTO.of(memberId, cart);
    }

    public CartResponseDTO deleteItem(Long memberId, Long cartItemId) {
        Cart cart = cartRepository.findByMember_Id(memberId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "장바구니가 없습니다."));

        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "장바구니 상품을 찾을 수 없습니다."
                ));

        validateCartOwnership(cart, item);

        cart.removeItem(item);

        return CartResponseDTO.of(memberId, cartRepository.save(cart));
    }

    private Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "회원을 찾을 수 없습니다."
                ));
    }

    private Inventory getInventoryOrThrow(Sku sku) {
        if (sku.getInventory() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "재고 정보가 없습니다."
            );
        }
        return sku.getInventory();
    }

    private void validateStockForAdd(Inventory inventory, int totalQty) {
        if (totalQty < 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수량은 1개 이상이어야 합니다.");
        }

        Integer stock = inventory.getStockQty();
        if (stock == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "재고 정보가 없습니다.");
        }

        if (totalQty > stock) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "장바구니 수량이 재고를 초과했습니다."
            );
        }
    }

    private void validateStockForUpdate(Inventory inventory, int nextQty) {
        if (nextQty < 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수량은 1개 이상이어야 합니다.");
        }

        Integer stock = inventory.getStockQty();
        if (stock == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "재고 정보가 없습니다.");
        }

        if (nextQty > stock) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "변경 수량이 재고를 초과했습니다."
            );
        }
    }

    private void validateSkuActive(Sku sku) {
        if (sku.getIsActive() != null && !sku.getIsActive()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "판매 중인 옵션만 담을 수 있습니다.");
        }
    }

    private void validateCartOwnership(Cart cart, CartItem item) {
        if (item.getCart() == null || !item.getCart().getId().equals(cart.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "본인 장바구니만 수정할 수 있습니다.");
        }
    }
}