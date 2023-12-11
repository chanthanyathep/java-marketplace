package com.prior.marketplace.model;

import lombok.Data;

@Data
public class InboxModel {
    private String title;
    private String message;
    private int userId;
}
