package com.prior.marketplace.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prior.marketplace.component.kafka.kafkacomponent.KafkaProducerComponent;
import com.prior.marketplace.entity.InventoryEntity;
import com.prior.marketplace.exception.CustomException;
import com.prior.marketplace.model.InboxModel;
import com.prior.marketplace.model.MarketplaceModel;
import com.prior.marketplace.model.ResponseModel;
import com.prior.marketplace.model.insert.MarketplaceInsertModel;
import com.prior.marketplace.repository.MarketplaceNativeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MarketplaceService {
    @Value("${kafka.topics.inbox}")
    private String topic;
    private KafkaProducerComponent kafkaComponent;
    private MarketplaceNativeRepository marketplaceRepository;

    public MarketplaceService(KafkaProducerComponent kafkaComponent, MarketplaceNativeRepository marketplaceNativeRepository) {
        this.kafkaComponent = kafkaComponent;
        this.marketplaceRepository = marketplaceNativeRepository;
    }

    public ResponseModel<String> insertItemToMarketplace(MarketplaceInsertModel marketplaceInsertModel) throws  CustomException{
        ResponseModel<String>responseModel = new ResponseModel<>();
        responseModel.setStatus(200);
        responseModel.setDescription("ok");
        try {
            int inventoryId = marketplaceInsertModel.getInventoryId();
            InventoryEntity inventoryEntity = this.marketplaceRepository.getItemFromInventory(inventoryId);
            int itemId = inventoryEntity.getItem_id();
            int userId = inventoryEntity.getUser_id();
            int rowUpdated =  this.marketplaceRepository.insertItemToMarketplace(itemId,userId);
            this.marketplaceRepository.deleteItemFromInventory(inventoryId);
            InboxModel inboxModel = genInboxModel(userId);
            String msg = objectToJsonString(inboxModel);
            kafkaComponent.sendData(msg,topic);
            responseModel.setData("Your Item Has Been Successfully Posted");
        } catch (Exception ex){
            throw new CustomException(ex.getMessage());
        }
        return responseModel;
    }

    public ResponseModel<List<MarketplaceModel>> getItemsFromMarketPlace() throws  CustomException{
        ResponseModel<List<MarketplaceModel>> responseModel = new ResponseModel<>();
        responseModel.setStatus(200);
        responseModel.setDescription("ok");
        try {
            List<MarketplaceModel> marketplaceModels = this.marketplaceRepository.getItemsFromMarketplace();
            responseModel.setData(marketplaceModels);
        } catch (Exception ex){
            throw new CustomException(ex.getMessage());
        }
        return responseModel;
    }

    public ResponseModel<List<MarketplaceModel>> getItemsFromMarketPlaceBySellId(int sellId) throws  CustomException{
        ResponseModel<List<MarketplaceModel>> responseModel = new ResponseModel<>();
        responseModel.setStatus(200);
        responseModel.setDescription("ok");
        try {
            List<MarketplaceModel> marketplaceModels =  this.marketplaceRepository.getItemsFromMarketplaceBySeller(sellId);
            responseModel.setData(marketplaceModels);
        } catch (Exception ex){
            throw new CustomException(ex.getMessage());
        }
        return responseModel;
    }

    private String objectToJsonString(Object model) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(model);
    }

    private InboxModel genInboxModel(int userId) {
        InboxModel inboxModel = new InboxModel();
        inboxModel.setTitle("Your Item Has Been Successfully Posted!");
        inboxModel.setMessage("Congratulations! Your Item " +
                "has been successfully posted on the marketplace. " +
                "Your listing is now live and visible to potential " +
                "buyers. Get ready to connect with interested customers " +
                "and make a great sale! Good luck");
        inboxModel.setUserId(userId);
    return inboxModel;
    }
}
