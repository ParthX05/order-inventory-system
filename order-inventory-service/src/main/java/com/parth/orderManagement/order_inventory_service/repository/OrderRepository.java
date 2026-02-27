package com.parth.orderManagement.order_inventory_service.repository;

import com.parth.orderManagement.order_inventory_service.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
