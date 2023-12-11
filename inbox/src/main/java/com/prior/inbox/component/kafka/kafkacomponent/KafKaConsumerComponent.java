package com.prior.inbox.component.kafka.kafkacomponent;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.prior.inbox.service.InboxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class KafKaConsumerComponent {

    private InboxService inboxService;

    public KafKaConsumerComponent(InboxService inboxService) {
        this.inboxService = inboxService;
    }


    @KafkaListener(topics = "${kafka.topics.inbox}",groupId = "${kafka.groupId.inbox}")
    public void test(@Payload String message) throws JsonProcessingException {
        this.inboxService.insertInbox(message);
    }
}