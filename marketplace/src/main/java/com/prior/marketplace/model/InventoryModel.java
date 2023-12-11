package com.prior.marketplace.model;

import lombok.Data;

import java.util.List;

@Data
public class InventoryModel {
    private double balance;
    private List<InventoryItemModel> items;
}
