package com.parth.orderManagement.order_inventory_service.controller;

import com.parth.orderManagement.order_inventory_service.entity.Inventory;
import com.parth.orderManagement.order_inventory_service.service.InventoryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public Inventory addInventory(@RequestBody Inventory inventory){
        return inventoryService.saveInventory(inventory);
    }

    @GetMapping("/{productId}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Inventory getInventory(@PathVariable Long productId){
        return inventoryService.getInventory(productId);
    }
}
