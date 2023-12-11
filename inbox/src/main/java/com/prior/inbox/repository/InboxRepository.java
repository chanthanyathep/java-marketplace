package com.prior.inbox.repository;

import com.prior.inbox.entity.InboxEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InboxRepository {
    public int insertInbox(InboxEntity inboxEntity);
    public List<InboxEntity> getInboxByUserId(int userId);

    public List<InboxEntity> getInboxByInboxId(int inboxId);

    public int updateReadStatus(int inboxId);
}
