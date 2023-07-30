package com.fintech.bankaplication.service;

import com.fintech.bankaplication.dto.*;

public interface UserService {
    BankResponse createAccount(UserRequest userRequest);

    BankResponse balanceEnquiry(EnquiryRequest request);

    String nameEnquiry(EnquiryRequest request);

    BankResponse debitAccount(CreditDebitRequest debitRequest);
    BankResponse creditAccount(CreditDebitRequest creditRequest);

    BankResponse transfer(TransferRequest transferRequest);


}
