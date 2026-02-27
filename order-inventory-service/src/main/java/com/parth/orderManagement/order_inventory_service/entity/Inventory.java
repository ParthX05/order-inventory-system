package com.parth.orderManagement.order_inventory_service.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "inventory")
public class Inventory {
    @Id
    private Long productId;

    @Column(nullable = false)
    private int totalStock;

    @Column(nullable = false)
    private int reservedStock;

    @Version
    private Long version;

    public Inventory(){}

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getTotalStock() {
        return totalStock;
    }

    public void setTotalStock(int totalStock) {
        if (totalStock < 0) {
            throw new IllegalArgumentException("Total stock cannot be negative");
        }
        this.totalStock = totalStock;
    }

    public int getReservedStock() {
        return reservedStock;
    }

    public void setReservedStock(int reservedStock) {
        if (reservedStock < 0) {
            throw new IllegalArgumentException("Reserved stock cannot be negative");
        }
        if (reservedStock > this.totalStock) {
            throw new IllegalStateException("Reserved stock cannot exceed total stock");
        }
        this.reservedStock = reservedStock;
    }
}
