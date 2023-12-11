package com.prior.marketplace.repository;

import com.prior.marketplace.model.InventoryModel;
import com.prior.marketplace.model.insert.InventoryInsertModel;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryNativeRepository {
    public int insertItemToInventory(InventoryInsertModel inventoryInsertModel);
    public InventoryModel getInventoryForUser(int userId);
}
