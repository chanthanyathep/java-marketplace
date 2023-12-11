package com.prior.marketplace.repository.impl;

import com.prior.marketplace.model.UserModel;
import com.prior.marketplace.repository.UserNativeRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserNativeRepositoryImpl implements UserNativeRepository {
    private JdbcTemplate jdbcTemplate;

    public UserNativeRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int insertUser(String username) {
        String sql = "INSERT INTO user (username,balance) VALUES (?,0)";
        int insertedRow = this.jdbcTemplate.update(sql, username);
        return insertedRow;
    }

    public int updateBalanceByUserId(int userId,double balance) {
        String sql = "UPDATE user SET balance = ? WHERE user_id = ?";
        int insertedRow = this.jdbcTemplate.update(sql,balance,userId);
        return insertedRow;
    }

    public List<UserModel> getAllUsers() {
        String sql = "SELECT user_id, username,balance FROM user";
            return jdbcTemplate.query(sql, (rs, rowNum) -> {
                UserModel user = new UserModel();
                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setBalance(rs.getDouble("balance"));
                return user;
            });

    }

}
