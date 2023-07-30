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
public class AccountInfo {
    private BigDecimal accountBalance;
    private String accountNumber;
    private String accountName;

}
