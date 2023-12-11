package com.prior.marketplace.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prior.marketplace.component.kafka.kafkacomponent.KafkaProducerComponent;
import com.prior.marketplace.entity.MarketplaceEntity;
import com.prior.marketplace.exception.CustomException;
import com.prior.marketplace.model.InboxModel;
import com.prior.marketplace.model.ResponseModel;
import com.prior.marketplace.model.TransactionModel;
import com.prior.marketplace.model.insert.TransactionInsertModel;
import com.prior.marketplace.repository.TransactionNativeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TransactionService {

    @Value("${kafka.topics.inbox}")
    private String topic;
   private TransactionNativeRepository transactionRepository;
   private KafkaProducerComponent kafkaProducerComponent;

    public TransactionService(TransactionNativeRepository transactionNativeRepository, KafkaProducerComponent kafkaProducerComponent) {
        this.transactionRepository = transactionNativeRepository;
        this.kafkaProducerComponent = kafkaProducerComponent;
    }

    public ResponseModel<String>  insertTransaction(TransactionInsertModel transactionInsertModel) throws CustomException {
        ResponseModel<String> responseModel = new ResponseModel<>();
        responseModel.setStatus(200);
        responseModel.setDescription("ok");
        try {
            MarketplaceEntity marketplaceEntity = this.transactionRepository.getMarketplace(transactionInsertModel.getMarketplaceId());

            int sellerId = marketplaceEntity.getSellerId();
            int buyerId = transactionInsertModel.getBuyerId();
            if(sellerId == buyerId) {
                throw new CustomException("You can't buy your item");
            }
            double balance = this.transactionRepository.getBalanceByBuyerId(buyerId);

            int itemId = marketplaceEntity.getItemId();
            double itemPrice = this.transactionRepository.getTotalPrice(itemId);
            if(balance - itemPrice >= 0){
                  this.transactionRepository.insertTransaction(itemId,buyerId,sellerId);
                  this.transactionRepository.insertItemToInventory(itemId,buyerId);
                  this.transactionRepository.updateBuyerBalance(buyerId,itemPrice);
                  this.transactionRepository.updateSellerBalance(sellerId,itemPrice);
                  this.transactionRepository.updateMarketplaceStatus(transactionInsertModel.getMarketplaceId());

                  InboxModel buyerInboxModel = genBuyerInboxModel(buyerId);
                  String message = objectToJsonString(buyerInboxModel);
                  this.kafkaProducerComponent.sendData(message,topic);

                  InboxModel sellerInboxModel = genSellerInboxModel(sellerId);
                  message = objectToJsonString(sellerInboxModel);
                this.kafkaProducerComponent.sendData(message,topic);
                responseModel.setData("Your Purchase Was Successful");
            }
            else{
                throw new CustomException("not enough money");
            }
        } catch (Exception ex){
            throw new CustomException(ex.getMessage());
        }
        return responseModel;
    }

    public ResponseModel<List<TransactionModel>> getTransactionBySellerId(int sellerId) throws CustomException {
        ResponseModel<List<TransactionModel>> responseModel = new ResponseModel<>();
        responseModel.setStatus(200);
        responseModel.setDescription("ok");
        try {
            List<TransactionModel>  transactionModels = this.transactionRepository.getTransactionsBySellerId(sellerId);
            responseModel.setData(transactionModels);
        } catch (Exception ex){
            throw new CustomException(ex.getMessage());
        }
        return responseModel;
    }

    public ResponseModel<List<TransactionModel>> getTransactionByBuyerId(int buyerId) throws CustomException {
        ResponseModel<List<TransactionModel>> responseModel = new ResponseModel<>();
        responseModel.setStatus(200);
        responseModel.setDescription("ok");
        try {
            List<TransactionModel>  transactionModels = this.transactionRepository.getTransactionsByBuyerId(buyerId);
            responseModel.setData(transactionModels);
        } catch (Exception ex){
            throw new CustomException(ex.getMessage());
        }
        return responseModel;
    }

    private InboxModel genBuyerInboxModel(int userId) {
        InboxModel inboxModel = new InboxModel();
        inboxModel.setTitle("Your Purchase Was Successful!");
        inboxModel.setMessage("Congratulations on your purchase of Item! " +
                "Your transaction is complete, and the item is now yours. " +
                "Thank you for using our marketplace. Enjoy your new purchase " +
                "and feel free to reach out if you have any questions " +
                "or need further assistance");
        inboxModel.setUserId(userId);
        return inboxModel;
    }

    private InboxModel genSellerInboxModel(int userId) {
        InboxModel inboxModel = new InboxModel();
        inboxModel.setTitle("Your Item Has Been Sold!");
        inboxModel.setMessage("Great news! Your Item has been successfully sold." +
                " You have a new buyer and your transaction is complete. " +
                "Please proceed with shipping the item promptly to ensure a " +
                "positive buyer experience.Thank you for using our marketplace");
        inboxModel.setUserId(userId);
        return inboxModel;
    }

    private String objectToJsonString(Object model) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(model);
    }
}
