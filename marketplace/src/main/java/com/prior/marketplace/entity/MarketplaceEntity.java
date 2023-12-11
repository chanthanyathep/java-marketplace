package com.prior.marketplace.entity;

import lombok.Data;

@Data
public class MarketplaceEntity {
    private int marketplaceId;
    private int itemId;
    private String status;
    private int sellerId;
}
