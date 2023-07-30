package com.fintech.bankaplication.controller;

import com.fintech.bankaplication.dto.*;
import com.fintech.bankaplication.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Tag(name="User Account Management APIs")
public class UserController {
    @Autowired
    UserService userService;
@Operation(
        summary = "Create New User Account",
        description = "Creating a new user and assigning an account ID"
)
    @PostMapping
    public BankResponse createAccount(@RequestBody UserRequest userRequest){
        return  userService.createAccount(userRequest);
    }

    @GetMapping("/balanceEnquiry")
    public BankResponse balanceEnquiry(@RequestBody EnquiryRequest enquiryRequest){
        return userService.balanceEnquiry(enquiryRequest);
    }

    @GetMapping("nameEnquiry")
    public  String nameEnquiry(@RequestBody EnquiryRequest enquiryRequest ){
        return userService.nameEnquiry(enquiryRequest);
    }

    @PostMapping("/debitAccount")
    public  BankResponse debitAccount(@RequestBody CreditDebitRequest debitRequest){
        return  userService.debitAccount(debitRequest);
    }

    @PostMapping("/creditAccount")
    public  BankResponse creditAccount(@RequestBody CreditDebitRequest creditDebitRequest){
        return  userService.creditAccount(creditDebitRequest);
    }

    @PostMapping("/transfer")
    public  BankResponse transfer(@RequestBody TransferRequest transferRequest){
        return  userService.transfer(transferRequest);
    }


}
