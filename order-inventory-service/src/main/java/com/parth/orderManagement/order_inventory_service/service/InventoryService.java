package com.parth.orderManagement.order_inventory_service.service;

import com.parth.orderManagement.order_inventory_service.entity.Inventory;
import com.parth.orderManagement.order_inventory_service.exception.InsufficientStockException;
import com.parth.orderManagement.order_inventory_service.exception.ProductNotFoundException;
import com.parth.orderManagement.order_inventory_service.repository.InventoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    public InventoryService(InventoryRepository inventoryRepository){
        this.inventoryRepository = inventoryRepository;
    }

    private Inventory getInventoryOrThrow(Long productId) {
        if (productId == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }
        return inventoryRepository.findByIdForUpdate(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }

    @Transactional
    public void reserveStock(Long productId, int quantity){
        Inventory inventory = getInventoryOrThrow(productId);

        int availableStock = inventory.getTotalStock() - inventory.getReservedStock();

        if(availableStock < quantity){
            throw new InsufficientStockException("Insufficient stock");
        }

        inventory.setReservedStock(
                inventory.getReservedStock() + quantity
        );
        inventoryRepository.save(inventory);
    }

    @Transactional
    public void confirmStock(Long productId, int quantity){
        Inventory inventory = getInventoryOrThrow(productId);

        if (inventory.getReservedStock() < quantity) {
            throw new RuntimeException("Not enough reserved stock");
        }
        if (inventory.getTotalStock() < quantity) {
            throw new RuntimeException("Not enough total stock");
        }
        inventory.setReservedStock(
                inventory.getReservedStock() - quantity
        );
        inventory.setTotalStock(
                inventory.getTotalStock() - quantity
        );
        inventoryRepository.save(inventory);
    }

    @Transactional
    public void releaseStock(Long productId, int quantity){
        Inventory inventory = getInventoryOrThrow(productId);

        if (inventory.getReservedStock() < quantity) {
            throw new RuntimeException("Not enough reserved stock to release");
        }
        inventory.setReservedStock(
                inventory.getReservedStock() - quantity
        );
        inventoryRepository.save(inventory);
    }
}
