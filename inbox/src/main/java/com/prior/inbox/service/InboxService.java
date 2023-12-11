package com.prior.inbox.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prior.inbox.component.Transom;
import com.prior.inbox.exception.CustomException;
import com.prior.inbox.entity.InboxEntity;
import com.prior.inbox.model.MessageModel;
import com.prior.inbox.model.TitleModel;
import com.prior.inbox.model.ResponseModel;
import com.prior.inbox.repository.InboxRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class InboxService {
    private InboxRepository inboxRepository;
    private Transom transom;

    public InboxService(InboxRepository inboxRepository, Transom transom) {
        this.inboxRepository = inboxRepository;
        this.transom = transom;
    }

    public ResponseModel<Integer> insertInbox(String message) throws CustomException {
        ResponseModel<Integer> responseModel = new ResponseModel<>();
        responseModel.setStatus(200);
        responseModel.setDescription("ok");
        try {
            InboxEntity inboxEntity = genMessageToObject(message);
            int rowUpdated = this.inboxRepository.insertInbox(inboxEntity);
            responseModel.setData(rowUpdated);
        } catch (Exception ex){
            log.error(ex.getMessage());
        }
        return responseModel;
    }

    public InboxEntity genMessageToObject(String message) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        InboxEntity inboxEntity = objectMapper.readValue(message, InboxEntity.class);
        log.info("inboxModel: {}", inboxEntity);
        return inboxEntity;
    }

    public ResponseModel<List<TitleModel>> getTitleModelByUserId(int userId){
        ResponseModel<List<TitleModel>> responseModel = new ResponseModel<>();
        responseModel.setStatus(200);
        responseModel.setDescription("ok");
        try{
            List<InboxEntity> inboxEntity = this.inboxRepository.getInboxByUserId(userId);
            List<TitleModel> titleModelList = this.transom.tranformInboxEitityToTitleModelList(inboxEntity);
            responseModel.setData(titleModelList);
        }catch (Exception ex){
            throw new CustomException(ex.getMessage());
        }
        return responseModel;
    }

    public ResponseModel<List<MessageModel>> getMessageModelByInboxId(int inboxId){
        ResponseModel<List<MessageModel>> responseModel = new ResponseModel<>();
        responseModel.setStatus(200);
        responseModel.setDescription("ok");
        try{
            List<InboxEntity> inboxEntity = this.inboxRepository.getInboxByInboxId(inboxId);
            List<MessageModel> messageModelList = this.transom.tranformInboxEitityToMessageModelList(inboxEntity);
            this.inboxRepository.updateReadStatus(inboxId);
            responseModel.setData(messageModelList);
        }catch (Exception ex){
            throw new CustomException(ex.getMessage());
        }
        return responseModel;
    }
}
