package com.prior.marketplace.service;

import com.prior.marketplace.exception.CustomException;
import com.prior.marketplace.model.ItemModel;
import com.prior.marketplace.model.ResponseModel;
import com.prior.marketplace.repository.ItemNativeRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    private final ItemNativeRepository itemRepository;

    public ItemService(ItemNativeRepository itemNativeRepository) {
        this.itemRepository = itemNativeRepository;
    }

    public ResponseModel<ItemModel>getItem(int itemId) throws CustomException {
        ResponseModel<ItemModel> responseModel = new ResponseModel<>();
        responseModel.setStatus(200);
        responseModel.setDescription("ok");
        try{
            ItemModel item = this.itemRepository.getItem(itemId);
            responseModel.setData(item);
        }catch (Exception ex){
            throw new CustomException(ex.getMessage());
        }
        return responseModel;
    }

    public ResponseModel<List<ItemModel>> getItemList() throws CustomException {
        ResponseModel<List<ItemModel>> responseModel = new ResponseModel<>();
        responseModel.setStatus(200);
        responseModel.setDescription("ok");
        try{
            List<ItemModel> item = this.itemRepository.getItemList();
            responseModel.setData(item);
        }catch (Exception ex){
            throw new CustomException(ex.getMessage());
        }
        return responseModel;
    }

    public ResponseModel<Integer> insertItem(ItemModel itemModel) throws CustomException {
        ResponseModel<Integer> responseModel = new ResponseModel<>();
        responseModel.setStatus(200);
        responseModel.setDescription("ok");
        try{
            if(StringUtils.isEmpty(itemModel.getItemName()) || Double.isNaN(itemModel.getPrice())){
                throw new CustomException("Bad Request: Name or Price is empty/invalid");
            }
            int rowUpdated = this.itemRepository.insertItem(itemModel);
            responseModel.setData(rowUpdated);
        }catch (Exception ex){
            throw new CustomException(ex.getMessage());
        }
        return responseModel;
    }
}
