package com.prior.marketplace.repository;

import com.prior.marketplace.model.ItemModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemNativeRepository {
    public int insertItem(ItemModel itemModel);
    public ItemModel getItem(int itemId);
    public List<ItemModel> getItemList();
}
