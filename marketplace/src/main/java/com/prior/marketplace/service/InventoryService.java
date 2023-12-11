package com.prior.marketplace.service;

import com.prior.marketplace.exception.CustomException;
import com.prior.marketplace.model.InventoryModel;
import com.prior.marketplace.model.ResponseModel;
import com.prior.marketplace.model.insert.InventoryInsertModel;
import com.prior.marketplace.repository.InventoryNativeRepository;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {
    private InventoryNativeRepository inventoryRepository;

    public InventoryService(InventoryNativeRepository inventoryNativeRepository) {
        this.inventoryRepository = inventoryNativeRepository;
    }

    public ResponseModel<String> insertInventory(InventoryInsertModel inventModel) throws CustomException {
        ResponseModel<String> responseModel = new ResponseModel<>();
        responseModel.setStatus(200);
        responseModel.setDescription("ok");
        try{
            int rowUpdated = this.inventoryRepository.insertItemToInventory(inventModel);
            responseModel.setData("Your Item Has Been Successfully Added");
        }catch (Exception ex){
            throw new CustomException(ex.getMessage());
        }
        return  responseModel;
    }

    public ResponseModel<InventoryModel> getInventoryByUser(int userId) throws CustomException {
        ResponseModel<InventoryModel> responseModel = new ResponseModel<>();
        responseModel.setStatus(200);
        responseModel.setDescription("ok");
        try {
           InventoryModel inventory = this.inventoryRepository.getInventoryForUser(userId);
           responseModel.setData(inventory);
        }catch (Exception ex){
            throw new CustomException(ex.getMessage());
        }
        return  responseModel;
    }
}
