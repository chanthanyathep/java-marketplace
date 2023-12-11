package com.prior.marketplace.controller.rest;

import com.prior.marketplace.model.ResponseModel;
import com.prior.marketplace.model.TransactionModel;
import com.prior.marketplace.model.insert.TransactionInsertModel;
import com.prior.marketplace.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {
    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/buyItem")
    public ResponseModel<String> buyItem(@RequestBody TransactionInsertModel transactionInsertModel) {
        return this.transactionService.insertTransaction(transactionInsertModel);
    }

    @GetMapping("/buyer/{buyerId}")
    public ResponseModel<List<TransactionModel>> getTransactionsByBuyerId(@PathVariable int buyerId) {
        return this.transactionService.getTransactionByBuyerId(buyerId);
    }

    @GetMapping("/seller/{sellerId}")
    public ResponseModel<List<TransactionModel>> getTransactionsBySellerId(@PathVariable int sellerId) {
        return this.transactionService.getTransactionBySellerId(sellerId);
    }

}
