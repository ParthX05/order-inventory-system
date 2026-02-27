package com.parth.orderManagement.order_inventory_service.dto;

import com.parth.orderManagement.order_inventory_service.enums.OrderStatus;

public record OrderResponse (
        Long orderId,
        Long productId,
        int quantity,
        OrderStatus status
){}
