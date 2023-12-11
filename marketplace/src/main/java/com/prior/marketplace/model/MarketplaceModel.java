package com.prior.marketplace.model;

import lombok.Data;

@Data
public class MarketplaceModel {
    private int marketplaceId;
    private String itemName;
    private String status;
    private double price;
    private int sellerId;
}
