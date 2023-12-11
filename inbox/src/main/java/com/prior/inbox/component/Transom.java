package com.prior.inbox.component;

import com.prior.inbox.entity.InboxEntity;
import com.prior.inbox.model.MessageModel;
import com.prior.inbox.model.TitleModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Transom {

    public List<TitleModel> tranformInboxEitityToTitleModelList(List<InboxEntity> inboxEntityList) {
        List<TitleModel> titleModelList = new ArrayList<>();
        for (InboxEntity x : inboxEntityList) {
            TitleModel titleModel = transformEntityToTitleModel(x);
            titleModelList.add(titleModel);
        }
        return titleModelList;
    }
    public TitleModel transformEntityToTitleModel(InboxEntity inboxEntity) {
        TitleModel titleModel = new TitleModel();
        titleModel.setInboxId(inboxEntity.getInboxId());
        titleModel.setStatus(inboxEntity.getStatus());
        titleModel.setTitle(inboxEntity.getTitle());
        titleModel.setCreateDate(inboxEntity.getCreateDate());
        return titleModel;
    }

    public List<MessageModel> tranformInboxEitityToMessageModelList(List<InboxEntity> inboxEntityList) {
        List<MessageModel> messageModelList = new ArrayList<>();
        for (InboxEntity x : inboxEntityList) {
            MessageModel messageModel = transformEntityToMessageModel(x);
            messageModelList.add(messageModel);
        }
        return messageModelList;
    }

    public MessageModel transformEntityToMessageModel(InboxEntity inboxEntity) {
        MessageModel messageModel = new MessageModel();
        messageModel.setTitle(inboxEntity.getTitle());
        messageModel.setMessage(inboxEntity.getMessage());
        return messageModel;
    }
}
