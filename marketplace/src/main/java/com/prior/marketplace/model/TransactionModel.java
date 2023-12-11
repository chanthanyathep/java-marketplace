package com.prior.marketplace.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TransactionModel {
    private int transactionId;
    private String itemName;
    private int buyerId;
    private int sellerId;
    private double price;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate createDate;
}
