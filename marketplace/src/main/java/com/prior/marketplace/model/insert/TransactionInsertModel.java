package com.prior.marketplace.model.insert;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TransactionInsertModel {
    private int marketplaceId;
    private int buyerId;
}
