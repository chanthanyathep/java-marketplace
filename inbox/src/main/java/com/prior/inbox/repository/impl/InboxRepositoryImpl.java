package com.prior.inbox.repository.impl;

import com.prior.inbox.entity.InboxEntity;
import com.prior.inbox.repository.InboxRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class InboxRepositoryImpl implements InboxRepository {
    private JdbcTemplate jdbcTemplate;

    public InboxRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int insertInbox(InboxEntity inboxEntity) {
        String sql = " INSERT INTO inbox (user_id,title,message) " +
                " VALUES (?,?,?) ";
        return jdbcTemplate.update(sql,
                inboxEntity.getUserId(),
                inboxEntity.getTitle(),
                inboxEntity.getMessage());
    }

    public List<InboxEntity> getInboxByUserId(int userId) {
        String sql = "SELECT * FROM inbox WHERE user_id = ?";
        return jdbcTemplate.query(sql, new Object[]{userId}, (rs, rowNum) -> {
            InboxEntity inboxEntity = new InboxEntity();
            inboxEntity.setInboxId(rs.getInt("inbox_id"));
            inboxEntity.setUserId(rs.getInt("user_id"));
            inboxEntity.setTitle(rs.getString("title"));
            inboxEntity.setMessage(rs.getString("message"));
            inboxEntity.setStatus(rs.getString("status"));
            inboxEntity.setCreateDate(rs.getObject("create_date", LocalDate.class));
            return inboxEntity;
        });
    }

    public List<InboxEntity> getInboxByInboxId(int inboxId) {
        String sql = "SELECT * FROM inbox WHERE inbox_id = ?";
        return jdbcTemplate.query(sql, new Object[]{inboxId}, (rs, rowNum) -> {
            InboxEntity inboxEntity = new InboxEntity();
            inboxEntity.setInboxId(rs.getInt("inbox_id"));
            inboxEntity.setUserId(rs.getInt("user_id"));
            inboxEntity.setTitle(rs.getString("title"));
            inboxEntity.setMessage(rs.getString("message"));
            inboxEntity.setStatus(rs.getString("status"));
            inboxEntity.setCreateDate(rs.getObject("create_date", LocalDate.class));
            return inboxEntity;
        });
    }

    public int updateReadStatus(int inboxId) {
        String sql = "UPDATE inbox SET status = 'read' WHERE inbox_id = ? ";
        return jdbcTemplate.update(sql,inboxId);
    }

}
