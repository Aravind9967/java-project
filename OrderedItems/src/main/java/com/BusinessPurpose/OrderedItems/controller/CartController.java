package com.BusinessPurpose.OrderedItems.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.BusinessPurpose.OrderedItems.DTO.AddToCartRequestDTO;
import com.BusinessPurpose.OrderedItems.DTO.CartResponseDTO;
import com.BusinessPurpose.OrderedItems.DTO.UpdateCartItemDTO;
import com.BusinessPurpose.OrderedItems.wrapper.ApiResponse;


import com.BusinessPurpose.OrderedItems.services.CartService;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<CartResponseDTO>> getCart(@PathVariable Long userId) {
        return ResponseEntity.ok(ApiResponse.Success("Cart retrieved", cartService.getCartByUserId(userId)));
    }
    @PostMapping("/{userId}/items")
    public ResponseEntity<ApiResponse<CartResponseDTO>> addToCart(
            @PathVariable Long userId,
            @Valid @RequestBody AddToCartRequestDTO request) {

        System.out.println("ProductId = " + request.getProductId());
        System.out.println("Quantity = " + request.getQuantity());

        CartResponseDTO cart = cartService.addItemToCart(userId, request);

        return ResponseEntity.ok(ApiResponse.Success("Item added to cart", cart));
    }
    @PutMapping("/{userId}/items/{itemId}")
    public ResponseEntity<ApiResponse<CartResponseDTO>> updateCartItem(
            @PathVariable Long userId,
            @PathVariable Long itemId,
            @Valid @RequestBody UpdateCartItemDTO request) {
        CartResponseDTO cart = cartService.updateCartItem(userId, itemId, request);
        return ResponseEntity.ok(ApiResponse.Success("Cart item updated", cart));
    }

    @DeleteMapping("/{userId}/items/{itemId}")
    public ResponseEntity<ApiResponse<CartResponseDTO>> removeCartItem(
            @PathVariable Long userId,
            @PathVariable Long itemId) {
        CartResponseDTO cart = cartService.removeItemFromCart(userId, itemId);
        return ResponseEntity.ok(ApiResponse.Success("Item removed from cart", cart));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<CartResponseDTO>> clearCart(@PathVariable Long userId) {
        CartResponseDTO cart = cartService.clearCart(userId);
        return ResponseEntity.ok(ApiResponse.Success("Cart cleared", cart));
    }
}
