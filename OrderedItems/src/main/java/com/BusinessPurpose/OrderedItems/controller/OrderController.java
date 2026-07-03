package com.BusinessPurpose.OrderedItems.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.BusinessPurpose.OrderedItems.DTO.CheckoutRequestDTO;
import com.BusinessPurpose.OrderedItems.DTO.OrderResponseDTO;
import com.BusinessPurpose.OrderedItems.entity.Order;
import com.BusinessPurpose.OrderedItems.services.OrderServices;
import com.BusinessPurpose.OrderedItems.wrapper.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderServices orderServices;

    @PostMapping("/checkout/{userId}")
    public ResponseEntity<ApiResponse<OrderResponseDTO>> checkout(
            @PathVariable Long userId,
            @Valid @RequestBody CheckoutRequestDTO request) {
        OrderResponseDTO order = orderServices.checkout(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.Success("Order placed successfully", order));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<OrderResponseDTO>>> getUserOrders(@PathVariable Long userId) {
        return ResponseEntity.ok(ApiResponse.Success("Orders retrieved", orderServices.getOrdersByUser(userId)));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<OrderResponseDTO>> getOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(ApiResponse.Success("Order retrieved", orderServices.getOrderById(orderId)));
    }

    @PatchMapping("/{orderId}/status")
    public ResponseEntity<ApiResponse<OrderResponseDTO>> updateStatus(
            @PathVariable Long orderId,
            @RequestParam Order.OrderStatus status) {
        return ResponseEntity.ok(ApiResponse.Success("Status updated",
                orderServices.updateOrderStatus(orderId, status)));
    }

    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<ApiResponse<OrderResponseDTO>> cancelOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(ApiResponse.Success("Order cancelled", orderServices.cancelOrder(orderId)));
    }
}
