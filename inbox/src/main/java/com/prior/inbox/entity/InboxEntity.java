package com.prior.inbox.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class InboxEntity {
    private int inboxId;
    private int userId;
    private String title;
    private String message;
    private String status;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate createDate;
}
