package com.prior.marketplace.repository.impl;

import com.prior.marketplace.model.InventoryItemModel;
import com.prior.marketplace.model.InventoryModel;
import com.prior.marketplace.model.insert.InventoryInsertModel;
import com.prior.marketplace.repository.InventoryNativeRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InventoryNativeRepositoryImpl implements InventoryNativeRepository {

    private JdbcTemplate jdbcTemplate;

    public InventoryNativeRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int insertItemToInventory(InventoryInsertModel inventoryInsertModel) {
        List<Object> paramList = new ArrayList<>();
        String sql = " INSERT INTO inventory (user_id,item_id) VALUES (?,?) ";
        paramList.add(inventoryInsertModel.getUserId());
        paramList.add(inventoryInsertModel.getItemId());
        return this.jdbcTemplate.update(sql,paramList.toArray());
    }

    @Override
    public InventoryModel getInventoryForUser(int userId) {
        InventoryModel inventoryModel = new InventoryModel();

        String balanceQuery = "SELECT balance FROM user WHERE user_id = ?";
        Double userBalance = jdbcTemplate.queryForObject(balanceQuery, Double.class, userId);

        String inventoryQuery = "SELECT inv.inventory_id,i.item_id, i.item_name, i.price " +
                "FROM inventory inv " +
                "JOIN item i ON inv.item_id = i.item_id " +
                "WHERE inv.user_id = ?";

        List<InventoryItemModel> items = jdbcTemplate.query(inventoryQuery, new Object[]{userId}, (rs, rowNum) -> {
            InventoryItemModel item = new InventoryItemModel();
            item.setInventoryId(rs.getInt("inventory_id"));
            item.setItemId(rs.getInt("item_id"));
            item.setItemName(rs.getString("item_name"));
            item.setPrice(rs.getDouble("price"));
            return item;
        });

        inventoryModel.setBalance(userBalance); // Set the user's balance
        inventoryModel.setItems(items); // Set inventory items

        return inventoryModel;
    }




}
