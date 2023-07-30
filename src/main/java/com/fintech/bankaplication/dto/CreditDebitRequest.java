package com.fintech.bankaplication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditDebitRequest {
    private  String accountNumber;
    private BigDecimal amount;
}
