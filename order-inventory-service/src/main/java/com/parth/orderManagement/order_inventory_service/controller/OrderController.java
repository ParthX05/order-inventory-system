package com.parth.orderManagement.order_inventory_service.controller;

import com.parth.orderManagement.order_inventory_service.dto.CreateOrderRequest;
import com.parth.orderManagement.order_inventory_service.dto.OrderResponse;
import com.parth.orderManagement.order_inventory_service.entity.Order;
import com.parth.orderManagement.order_inventory_service.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @GetMapping("/ping")
    public String ping(){
        return "ok";
    }

    @PostMapping("/create")
    public ResponseEntity<OrderResponse> createOrder(
            @Valid @RequestBody CreateOrderRequest request) {

        Order order = orderService.createOrder(
                request.productId(),
                request.quantity()
        );

        OrderResponse response = new OrderResponse(
                order.getOrderId(),
                order.getProductId(),
                order.getQuantity(),
                order.getStatus()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/{orderId}/payment")
    public ResponseEntity<Void> paymentSuccess(@PathVariable Long orderId) {
        orderService.markPaymentSuccess(orderId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{orderId}/confirm")
    public ResponseEntity<Void> confirmOrder(@PathVariable Long orderId) {
        orderService.confirmOrder(orderId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
        return ResponseEntity.ok().build();
    }
}
