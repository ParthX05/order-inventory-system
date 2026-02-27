package com.parth.orderManagement.order_inventory_service.entity;

import com.parth.orderManagement.order_inventory_service.enums.OrderStatus;
import jakarta.persistence.*;
import jakarta.persistence.GenerationType;


@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private int quantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Version
    private Long version;

    public Order(){}

    public Order(Long productId, int quantity) {
        if (productId == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }

        this.productId = productId;
        this.quantity = quantity;
        this.status = OrderStatus.CREATED;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void markPaymentSuccess() {
        if (this.status != OrderStatus.CREATED) {
            throw new IllegalStateException("Payment already processed or order invalid");
        }
        this.status = OrderStatus.PAYMENT_SUCCESS;
    }

    public void confirm() {
        if (this.status != OrderStatus.PAYMENT_SUCCESS) {
            throw new IllegalStateException("Order cannot be confirmed without payment");
        }
        this.status = OrderStatus.CONFIRMED;
    }

    public void cancel() {
        if (this.status == OrderStatus.CONFIRMED) {
            throw new IllegalStateException("Confirmed order cannot be cancelled");
        }
        if (this.status == OrderStatus.CANCELLED) {
            throw new IllegalStateException("Order already cancelled");
        }
        this.status = OrderStatus.CANCELLED;
    }
}
