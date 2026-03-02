package com.parth.orderManagement.order_inventory_service.service;

import com.parth.orderManagement.order_inventory_service.entity.Order;
import com.parth.orderManagement.order_inventory_service.enums.OrderStatus;
import com.parth.orderManagement.order_inventory_service.exception.OrderNotFoundException;
import com.parth.orderManagement.order_inventory_service.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryService inventoryService;

    public OrderService(OrderRepository orderRepository,
                        InventoryService inventoryService) {
        this.orderRepository = orderRepository;
        this.inventoryService = inventoryService;
    }

    private static final Map<OrderStatus, Set<OrderStatus>> ALLOWED_TRANSITIONS =
            Map.of(
                    OrderStatus.CREATED,
                    Set.of(OrderStatus.PAYMENT_SUCCESS, OrderStatus.CANCELLED),

                    OrderStatus.PAYMENT_SUCCESS,
                    Set.of(OrderStatus.CONFIRMED, OrderStatus.CANCELLED),

                    OrderStatus.CONFIRMED,
                    Set.of(),

                    OrderStatus.CANCELLED,
                    Set.of()
            );

    private void validateTransition(OrderStatus from, OrderStatus to) {
        if (!ALLOWED_TRANSITIONS
                .getOrDefault(from, Set.of())
                .contains(to)) {

            throw new IllegalStateException(
                    "Invalid transition from " + from + " to " + to
            );
        }
    }

    private Order getOrderOrThrow(Long orderId) {
        if (orderId == null) {
            throw new IllegalArgumentException("Order ID cannot be null");
        }

        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));
    }

    @Transactional
    public Order createOrder(Long productId, int quantity) {

        if (productId == null || quantity <= 0) {
            throw new IllegalArgumentException("Invalid product or quantity");
        }

        inventoryService.reserveStock(productId, quantity);

        Order order = new Order();
        order.setProductId(productId);
        order.setQuantity(quantity);
        order.setStatus(OrderStatus.CREATED);

        return orderRepository.save(order);
    }

    @Transactional
    public void markPaymentSuccess(Long orderId) {

        Order order = getOrderOrThrow(orderId);

        if (order.getStatus() == OrderStatus.PAYMENT_SUCCESS) {
            return;
        }

        validateTransition(order.getStatus(), OrderStatus.PAYMENT_SUCCESS);

        order.setStatus(OrderStatus.PAYMENT_SUCCESS);
    }

    @Transactional
    public void confirmOrder(Long orderId) {

        Order order = getOrderOrThrow(orderId);

        if (order.getStatus() == OrderStatus.CONFIRMED) {
            return;
        }

        validateTransition(order.getStatus(), OrderStatus.CONFIRMED);

        inventoryService.confirmStock(
                order.getProductId(),
                order.getQuantity()
        );

        order.setStatus(OrderStatus.CONFIRMED);
    }

    @Transactional
    public void cancelOrder(Long orderId) {

        Order order = getOrderOrThrow(orderId);

        if (order.getStatus() == OrderStatus.CANCELLED) {
            return;
        }

        OrderStatus previousStatus = order.getStatus();

        validateTransition(previousStatus, OrderStatus.CANCELLED);

        if (previousStatus == OrderStatus.CREATED ||
                previousStatus == OrderStatus.PAYMENT_SUCCESS) {

            inventoryService.releaseStock(
                    order.getProductId(),
                    order.getQuantity()
            );
        }

        order.setStatus(OrderStatus.CANCELLED);
    }
}

