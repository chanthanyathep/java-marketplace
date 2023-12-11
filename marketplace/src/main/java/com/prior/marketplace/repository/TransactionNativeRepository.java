package com.prior.marketplace.repository;

import com.prior.marketplace.entity.MarketplaceEntity;
import com.prior.marketplace.model.TransactionModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionNativeRepository {
    public int insertTransaction(int itemId,int buyerId,int sellerId);
    public List<TransactionModel> getTransactionsBySellerId(int sellerId);
    public List<TransactionModel> getTransactionsByBuyerId(int buyerId);

    public double getBalanceByBuyerId(int buyerId);

    public double getTotalPrice(int itemId);
    public int updateBuyerBalance(int userId, double price);
    public int updateSellerBalance(int userId, double price);

    public int updateMarketplaceStatus(int marketplaceId);

    public MarketplaceEntity getMarketplace(int marketplaceId);

    public int insertItemToInventory(int itemId,int userId);
}
