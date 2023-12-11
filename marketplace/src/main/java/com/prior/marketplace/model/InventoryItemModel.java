package com.prior.marketplace.model;

import lombok.Data;

@Data
public class InventoryItemModel {
    private int inventoryId;
    private int itemId;
    private String itemName;
    private double price;
}
