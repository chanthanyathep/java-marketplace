package com.prior.marketplace.controller.rest;

import com.prior.marketplace.model.InventoryModel;
import com.prior.marketplace.model.ResponseModel;
import com.prior.marketplace.model.insert.InventoryInsertModel;
import com.prior.marketplace.service.InventoryService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    private InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping("")
    public ResponseModel<String> insertInventory(@RequestBody InventoryInsertModel inventModel){
        return  this.inventoryService.insertInventory(inventModel);
    }

    @GetMapping("/{userId}")
    public ResponseModel<InventoryModel> insertInventory(@PathVariable int userId){
        return  this.inventoryService.getInventoryByUser(userId);
    }
}
