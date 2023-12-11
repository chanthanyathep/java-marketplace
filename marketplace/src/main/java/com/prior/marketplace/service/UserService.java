package com.prior.marketplace.service;

import com.prior.marketplace.exception.CustomException;
import com.prior.marketplace.model.ResponseModel;
import com.prior.marketplace.model.UserModel;
import com.prior.marketplace.repository.UserNativeRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private UserNativeRepository userNativeRepository;

    public UserService(UserNativeRepository userNativeRepository) {
        this.userNativeRepository = userNativeRepository;
    }

    public ResponseModel<String> insertUser(UserModel userModel) throws CustomException {
        ResponseModel<String> responseModel= new ResponseModel<>();
        responseModel.setStatus(200);
        responseModel.setDescription("ok");
        try {
            if(StringUtils.isEmpty(userModel.getUsername())){
                throw new CustomException("username cannot null or empty");
            }
            int rowInserted = this.userNativeRepository.insertUser(userModel.getUsername());
            if(rowInserted == 0){
                throw new CustomException("cannot insert user");
            }
            responseModel.setData("insert user successfully");
        } catch (Exception ex){
            throw new CustomException(ex.getMessage());
        }
        return responseModel;
    }
    public ResponseModel<String> updateBalance(UserModel userModel) throws CustomException {
        ResponseModel<String> responseModel= new ResponseModel<>();
        responseModel.setStatus(200);
        responseModel.setDescription("ok");
        try {
            if(userModel.getUserId() == 0){
                throw new CustomException("userId cannot null or empty");
            }
            int rowInserted = this.userNativeRepository.updateBalanceByUserId(userModel.getUserId(),userModel.getBalance());
            if(rowInserted == 0){
                throw new CustomException("cannot update balance");
            }
            responseModel.setData("update balance successfully");
        } catch (Exception ex){
            throw new CustomException(ex.getMessage());
        }
        return responseModel;
    }

    public ResponseModel<List<UserModel>> getAllUser() throws CustomException {
        ResponseModel<List<UserModel>> responseModel = new ResponseModel<>();
        responseModel.setStatus(200);
        responseModel.setDescription("ok");
        try {
           List<UserModel> userModelList = this.userNativeRepository.getAllUsers();
           responseModel.setData(userModelList);
        } catch (Exception ex){
            throw new CustomException(ex.getMessage());
        }
        return responseModel;
    }
}
