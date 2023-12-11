package com.prior.marketplace.repository;

import com.prior.marketplace.model.UserModel;

import java.util.List;

public interface UserNativeRepository {

    public int insertUser(String username);
    public List<UserModel> getAllUsers();

    public int updateBalanceByUserId(int userId,double balance);
}
