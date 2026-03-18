package com.parth.orderManagement.order_inventory_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.parth.orderManagement.order_inventory_service.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
