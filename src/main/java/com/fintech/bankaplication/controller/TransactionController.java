package com.fintech.bankaplication.controller;

import com.fintech.bankaplication.dto.BankResponse;
import com.fintech.bankaplication.dto.CreditDebitRequest;
import com.fintech.bankaplication.entity.Transaction;
import com.fintech.bankaplication.service.BankStatement;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transaction")
@AllArgsConstructor
public class TransactionController {
    private BankStatement bankStatement;

    @GetMapping
    public List<Transaction> generateStatement(@RequestParam String accountNumber,
            @RequestParam String startDate,
            @RequestParam String endDate){
        return  bankStatement.generateStatement(accountNumber, endDate, startDate);
    }


}
