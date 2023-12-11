package com.prior.marketplace.repository;

import com.prior.marketplace.entity.InventoryEntity;
import com.prior.marketplace.model.MarketplaceModel;
import com.prior.marketplace.model.insert.MarketplaceInsertModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarketplaceNativeRepository {
    public int insertItemToMarketplace(int itemId,int userId);
    public List<MarketplaceModel> getItemsFromMarketplace();
    public List<MarketplaceModel> getItemsFromMarketplaceBySeller(int sellerId);

    public InventoryEntity getItemFromInventory(int inventoryId);

    public int deleteItemFromInventory(int inventoryId);
}
