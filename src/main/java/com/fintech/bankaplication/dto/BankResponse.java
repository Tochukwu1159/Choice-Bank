package com.fintech.bankaplication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankResponse {
    private  String responseCode;
    private  String responseMessage;
    private  AccountInfo accountInfo;
}
