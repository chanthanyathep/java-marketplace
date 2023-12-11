package com.prior.marketplace.repository.impl;

import com.prior.marketplace.model.ItemModel;
import com.prior.marketplace.repository.ItemNativeRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ItemNativeRepositoryImpl implements ItemNativeRepository {

    private JdbcTemplate jdbcTemplate;

    public ItemNativeRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int insertItem(ItemModel itemModel) {
        List<Object> paramList = new ArrayList<>();
        String sql = " INSERT INTO item (item_name,price) VALUES (?,?) ";
        paramList.add(itemModel.getItemName());
        paramList.add(itemModel.getPrice());
        return this.jdbcTemplate.update(sql,paramList.toArray());
    }

    @Override
    public ItemModel getItem(int itemId) {
        String sql = "SELECT item_id, item_name, price FROM item WHERE item_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{itemId}, (rs, rowNum) -> {
            ItemModel item = new ItemModel();
            item.setItemId(rs.getInt("item_id"));
            item.setItemName(rs.getString("item_name"));
            item.setPrice(rs.getDouble("price"));
            return item;
        });
    }

    public List<ItemModel> getItemList() {
        String sql = "SELECT item_id, item_name, price FROM item";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            ItemModel item = new ItemModel();
            item.setItemId(rs.getInt("item_id"));
            item.setItemName(rs.getString("item_name"));
            item.setPrice(rs.getDouble("price"));
            return item;
        });
    }

}
