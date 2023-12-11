package com.prior.marketplace.repository.impl;

import com.prior.marketplace.entity.InventoryEntity;
import com.prior.marketplace.model.InventoryModel;
import com.prior.marketplace.model.MarketplaceModel;
import com.prior.marketplace.model.insert.MarketplaceInsertModel;
import com.prior.marketplace.repository.MarketplaceNativeRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MarketplaceNativeRepositoryImpl implements MarketplaceNativeRepository {
    private JdbcTemplate jdbcTemplate;

    public MarketplaceNativeRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int insertItemToMarketplace(int itemId,int userId) {
        String sql = "INSERT INTO marketplace (item_id, seller_id) VALUES (?, ?)";

        return jdbcTemplate.update(
                sql,itemId,userId
        );
    }
    public List<MarketplaceModel> getItemsFromMarketplace() {
        String sql = "SELECT m.marketplace_id, i.item_name, i.price AS item_price, m.seller_id, m.status " +
                "FROM marketplace m " +
                "JOIN item i ON m.item_id = i.item_id " +
                "WHERE m.status = 'available'";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            MarketplaceModel marketplaceModel = new MarketplaceModel();
            marketplaceModel.setMarketplaceId(rs.getInt("marketplace_id"));
            marketplaceModel.setItemName(rs.getString("item_name"));
            marketplaceModel.setPrice(rs.getDouble("item_price"));
            marketplaceModel.setSellerId(rs.getInt("seller_id"));
            marketplaceModel.setStatus(rs.getString("status"));
            return marketplaceModel;
        });
    }
    public List<MarketplaceModel> getItemsFromMarketplaceBySeller(int sellerId) {
        String sql = "SELECT m.marketplace_id, i.item_name, i.price AS item_price, m.seller_id, m.status " +
                "FROM marketplace m " +
                "JOIN item i ON m.item_id = i.item_id " +
                "JOIN user u ON m.seller_id = u.user_id " +
                "WHERE m.seller_id = ?";

        return jdbcTemplate.query(sql, new Object[]{sellerId}, (rs, rowNum) -> {
            MarketplaceModel marketplaceModel = new MarketplaceModel();
            marketplaceModel.setMarketplaceId(rs.getInt("marketplace_id"));
            marketplaceModel.setItemName(rs.getString("item_name"));
            marketplaceModel.setPrice(rs.getDouble("item_price"));
            marketplaceModel.setSellerId(rs.getInt("seller_id"));
            marketplaceModel.setStatus(rs.getString("status"));
            return marketplaceModel;
        });
    }

    public InventoryEntity getItemFromInventory(int inventoryId){
        String sql = "SELECT item_id,user_id FROM inventory WHERE inventory_id = ? ";
        return jdbcTemplate.queryForObject(sql,new Object[]{inventoryId},(rs, rowNum) -> {
           InventoryEntity inventoryEntity = new InventoryEntity();
           inventoryEntity.setInventoryId(inventoryId);
           inventoryEntity.setItem_id(rs.getInt("item_id"));
           inventoryEntity.setUser_id(rs.getInt("user_id"));
           return inventoryEntity;
        });
    }

    public int deleteItemFromInventory(int inventoryId){
        String sql = "DELETE FROM inventory WHERE inventory_id = ? ";
        return jdbcTemplate.update(sql,inventoryId);
    }

}
