package com.prior.inbox.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TitleModel {
    private int inboxId;
    private String title;
    private String status;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate createDate;
}
