package com.prior.inbox.controller.rest;

import com.prior.inbox.model.MessageModel;
import com.prior.inbox.model.TitleModel;
import com.prior.inbox.model.ResponseModel;
import com.prior.inbox.service.InboxService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/inbox")
public class InboxController {
    private InboxService inboxService;

    public InboxController(InboxService inboxService) {
        this.inboxService = inboxService;
    }
    @GetMapping("/getTitle/{userId}")
    public ResponseModel<List<TitleModel>> getTitleModelByUserId(@PathVariable int userId){
        return this.inboxService.getTitleModelByUserId(userId);
    }

    @GetMapping("/getMessage/{inboxId}")
    public ResponseModel<List<MessageModel>> getMessageModelByInboxId(@PathVariable int inboxId){
        return this.inboxService.getMessageModelByInboxId(inboxId);
    }
}
