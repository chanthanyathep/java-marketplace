package com.prior.marketplace.repository.impl;

import com.prior.marketplace.entity.MarketplaceEntity;
import com.prior.marketplace.model.TransactionModel;
import com.prior.marketplace.repository.TransactionNativeRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TransactionNativeRepositoryImpl implements TransactionNativeRepository {
    private JdbcTemplate jdbcTemplate;

    public TransactionNativeRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int insertTransaction(int itemId,int buyerId,int sellerId) {
        String sql = "INSERT INTO transaction (item_id, buyer_id, seller_id) VALUES (?, ?, ?)";

        return jdbcTemplate.update(sql,
                itemId,buyerId,sellerId);
    }

    public List<TransactionModel> getTransactionsBySellerId(int sellerId) {
        String sql = "SELECT t.transaction_id, t.buyer_id, t.seller_id, t.create_date, i.item_name, i.price " +
                "FROM transaction t " +
                "JOIN item i ON t.item_id = i.item_id " +
                "JOIN user u ON t.seller_id = u.user_id " +
                "WHERE t.seller_id = ?";

        return jdbcTemplate.query(sql, new Object[]{sellerId}, (rs, rowNum) -> {
            TransactionModel transactionModel = new TransactionModel();
            transactionModel.setTransactionId(rs.getInt("transaction_id"));
            transactionModel.setBuyerId(rs.getInt("buyer_id"));
            transactionModel.setSellerId(rs.getInt("seller_id"));
            transactionModel.setCreateDate(rs.getDate("create_date").toLocalDate());
            transactionModel.setItemName(rs.getString("item_name"));
            transactionModel.setPrice(rs.getDouble("price"));
            return transactionModel;
        });
    }
    public List<TransactionModel> getTransactionsByBuyerId(int buyerId) {
        String sql = "SELECT t.transaction_id, t.buyer_id, t.seller_id, t.create_date, i.item_name, i.price " +
                "FROM transaction t " +
                "JOIN item i ON t.item_id = i.item_id " +
                "JOIN user u ON t.seller_id = u.user_id " +
                "WHERE t.buyer_id = ?";

        return jdbcTemplate.query(sql, new Object[]{buyerId}, (rs, rowNum) -> {
            TransactionModel transactionModel = new TransactionModel();
            transactionModel.setTransactionId(rs.getInt("transaction_id"));
            transactionModel.setBuyerId(rs.getInt("buyer_id"));
            transactionModel.setSellerId(rs.getInt("seller_id"));
            transactionModel.setCreateDate(rs.getDate("create_date").toLocalDate());
            transactionModel.setItemName(rs.getString("item_name"));
            transactionModel.setPrice(rs.getDouble("price"));
            return transactionModel;
        });
    }

    public double getBalanceByBuyerId(int buyerId) {
        String sql = "SELECT balance FROM user " +
                "WHERE user_id = ?";
        return jdbcTemplate.queryForObject(sql, Double.class, buyerId);
    }

    public double getTotalPrice(int itemId){
        String sql = "SELECT i.price FROM item i WHERE item_id = ?";
        double price = jdbcTemplate.queryForObject(sql,Double.class,itemId);
        return price;
    }

    public int updateBuyerBalance(int userId, double price) {
        String sql = "UPDATE user SET balance = balance - ? WHERE user_id = ?";
        return jdbcTemplate.update(sql, price, userId);
    }

    public int updateSellerBalance(int userId, double price) {
        String sql = "UPDATE user SET balance = balance + ? WHERE user_id = ?";
        return jdbcTemplate.update(sql, price, userId);
    }

    public int updateMarketplaceStatus(int marketplaceId){
        String sql = "UPDATE marketplace SET status = 'sold' WHERE marketplace_id = ?";
        return jdbcTemplate.update(sql,marketplaceId);
    }

    public MarketplaceEntity getMarketplace(int marketplaceId) {
        String sql = "SELECT item_id,status,seller_id FROM marketplace WHERE marketplace_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{marketplaceId},(rs, rowNum) -> {
                MarketplaceEntity marketplaceEntity = new MarketplaceEntity();
                marketplaceEntity.setItemId(rs.getInt("item_id"));
                marketplaceEntity.setStatus(rs.getString("status"));
                marketplaceEntity.setSellerId(rs.getInt("seller_id"));
                marketplaceEntity.setMarketplaceId(marketplaceId);
                return marketplaceEntity;
        });
    }

    public int insertItemToInventory(int itemId,int userId) {
        String sql = " INSERT INTO inventory (user_id,item_id) VALUES (?,?) ";
        return this.jdbcTemplate.update(sql,userId,itemId);
    }




}
