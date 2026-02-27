package com.parth.orderManagement.order_inventory_service.dto;

import jakarta.validation.constraints.Min;
import org.antlr.v4.runtime.misc.NotNull;

public record CreateOrderRequest (
        @NotNull Long productId,
        @Min(1) int quantity
){}
