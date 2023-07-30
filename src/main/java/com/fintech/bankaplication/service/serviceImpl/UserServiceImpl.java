package com.fintech.bankaplication.service.serviceImpl;

import com.fintech.bankaplication.dto.*;
import com.fintech.bankaplication.entity.User;
import com.fintech.bankaplication.repository.UserRepository;
import com.fintech.bankaplication.service.EmailService;
import com.fintech.bankaplication.service.UserService;
import com.fintech.bankaplication.utils.AccountUtils;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    EmailService emailService;
    @Override
    public BankResponse createAccount(UserRequest userRequest) {
        if (userRepository.existsByEmail(userRequest.getEmail())) {

            return  BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_EXITS_MESSAGE)
                    .accountInfo(null)
                    .build();

        }
        User newUser = User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .otherName(userRequest.getOtherName())
                .gender(userRequest.getGender())
                .address(userRequest.getAddress())
                .stateOfOrigin(userRequest.getStateOfOrigin())
                .accountNumber(AccountUtils.generateAccountNumber())
                .email(userRequest.getEmail())
                .phoneNumber(userRequest.getPhoneNumber())
                .accountBalance(BigDecimal.ZERO)
                .alternativePhoneNumber(userRequest.getAlternativePhoneNumber())
                .status("ACTIVE")
                .build();
        User savedUser = userRepository.save(newUser);
        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(savedUser.getEmail())
                .subject("ACCOUNT CREATION")
                .messageBody("Congratulation! Your Account Has Been Successfully Created. \n Your Account Details: \n Account Name:" + savedUser.getFirstName() + " "+ savedUser.getLastName() + " " + savedUser.getOtherName() + "\nAccount Number: " + savedUser.getAccountNumber())
        .build();
//        emailService.sendEmailAlert(emailDetails);

        return BankResponse.builder()
                        .responseCode(AccountUtils.ACCOUNT_CREATION_SUCCESS)
                .responseMessage(AccountUtils.ACCOUNT_CREATION_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountBalance(savedUser.getAccountBalance())
                        .accountName(savedUser.getFirstName() + " " + savedUser.getLastName())
                        .accountNumber(savedUser.getAccountNumber())
                        .build())

        .build();
    }

    @Override
    public BankResponse balanceEnquiry(EnquiryRequest request) {
        boolean isAccountExists = userRepository.existsByAccountNumber(request.getAccountNumber());
        if(!isAccountExists){
            return BankResponse.builder().
            responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIT_MESSAGE)
                            .accountInfo(null)
                    .build();
        }
        User foundUser = userRepository.findByAccountNumber(request.getAccountNumber());
        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_FOUND_CODE)
                .responseMessage(AccountUtils.ACCOUNT_FOUND_SUCCESS)
                .accountInfo(
                        AccountInfo.builder()
                                .accountBalance(foundUser.getAccountBalance())
                                .accountNumber(foundUser.getAccountNumber())
                                .accountName(foundUser.getFirstName() + " " + foundUser.getLastName() + " " + foundUser.getOtherName())
                                .build()
                )
                .build();
    }

    @Override
    public String nameEnquiry(EnquiryRequest request) {

        boolean isAccountExists = userRepository.existsByAccountNumber(request.getAccountNumber());
        if(!isAccountExists){
            return AccountUtils.ACCOUNT_NOT_EXIT_MESSAGE;
        }
        User foundUser = userRepository.findByAccountNumber(request.getAccountNumber());
        return foundUser.getFirstName() + " " + foundUser.getLastName() +  " " + foundUser.getOtherName();

    }

    @Override
    public BankResponse debitAccount(CreditDebitRequest debitRequest) {
        boolean isAccountExists = userRepository.existsByAccountNumber(debitRequest.getAccountNumber());
        if(!isAccountExists) {
            return BankResponse.builder().
                    responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIT_MESSAGE)
                    .accountInfo(null)
                    .build();


        }
            User userToDebit = userRepository.findByAccountNumber(debitRequest.getAccountNumber());
            if(userToDebit.getAccountBalance().compareTo(debitRequest.getAmount())> 0){
                userToDebit.setAccountBalance(userToDebit.getAccountBalance().subtract(debitRequest.getAmount()));
                userRepository.save(userToDebit);
                return BankResponse.builder()
                        .responseMessage(AccountUtils.ACCOUNT_DEBITED_MESSAGE)
                        .responseCode(AccountUtils.ACCOUNT_DEBITED_SUCCESS)
                        .accountInfo(
                                AccountInfo.builder()
                                        .accountBalance(userToDebit.getAccountBalance())
                                        .accountNumber(userToDebit.getAccountNumber())
                                        .accountName(userToDebit.getFirstName() + " " + userToDebit.getLastName() + " " + userToDebit.getOtherName())
                                        .build()
                        )
                        .build();

            }else{
                return BankResponse.builder().
                responseCode(AccountUtils.INSUFICIENT_BALANCE_CODE)
                        .responseMessage(AccountUtils.INSUFICIENT_BALANCE_MESSAGE)
                                .accountInfo(null).
                        build();
            }

            }

    @Override
    public BankResponse creditAccount(CreditDebitRequest creditRequest) {
        boolean isAccountExists = userRepository.existsByAccountNumber(creditRequest.getAccountNumber());
        if(!isAccountExists) {
            return BankResponse.builder().
                    responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIT_MESSAGE)
                    .accountInfo(null)
                    .build();


        }
        User userToCredit = userRepository.findByAccountNumber(creditRequest.getAccountNumber());
        if(userToCredit.getAccountBalance().compareTo(BigDecimal.ZERO) >= 0){
            userToCredit.setAccountBalance(userToCredit.getAccountBalance().add(creditRequest.getAmount()));
            userRepository.save(userToCredit);
            return BankResponse.builder()
                    .responseMessage(AccountUtils.ACCOUNT_CREATION_MESSAGE)
                    .responseCode(AccountUtils.ACCOUNT_CREDITED_SUCCESS)
                    .accountInfo(
                            AccountInfo.builder()
                                    .accountBalance(userToCredit.getAccountBalance())
                                    .accountNumber(userToCredit.getAccountNumber())
                                    .accountName(userToCredit.getFirstName() + " " + userToCredit.getLastName() + " " + userToCredit.getOtherName())
                                    .build()
                    )
                    .build();

        }else{
            return BankResponse.builder().
                    responseCode(AccountUtils.INSUFICIENT_BALANCE_CODE)
                    .responseMessage(AccountUtils.INSUFICIENT_BALANCE_MESSAGE)
                    .accountInfo(null).
                    build();
        }
    }

    @Override
    public BankResponse transfer(TransferRequest transferRequest) {
        boolean isDestinationAccountExists= userRepository.existsByAccountNumber(transferRequest.getDestinationAccountNumber());
        if(!isDestinationAccountExists){
            return BankResponse.builder().
                    responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIT_MESSAGE)
                    .accountInfo(null)
                    .build();

        }
      User sourceAccountUser = userRepository.findByAccountNumber(transferRequest.getSourceAccountNumber());
        System.out.println(sourceAccountUser + "source account");
            if(transferRequest.getAmount().compareTo(sourceAccountUser.getAccountBalance()) > 0){
                return BankResponse.builder().
                        responseCode(AccountUtils.INSUFICIENT_BALANCE_CODE)
                        .responseMessage(AccountUtils.INSUFICIENT_BALANCE_MESSAGE)
                        .accountInfo(null).
                        build();

            }

        sourceAccountUser.setAccountBalance(sourceAccountUser.getAccountBalance().subtract(transferRequest.getAmount()));
            String sourceUsername = sourceAccountUser.getFirstName()+ " " + sourceAccountUser.getLastName() + " "+ sourceAccountUser.getOtherName();
            userRepository.save(sourceAccountUser);
            //build object of email
        EmailDetails debitAlert = EmailDetails.builder(). subject("DEBIT ALERT")
                .recipient(sourceAccountUser.getEmail())
                .messageBody("The sum of " + transferRequest.getAmount() + " has been debited from your account and your new account balance is " + sourceAccountUser.getAccountBalance())
                .build();
//        emailService.sendEmailAlert(debitAlert);

            User destinationAccountUser = userRepository.findByAccountNumber(transferRequest.getDestinationAccountNumber());
            destinationAccountUser.setAccountBalance(destinationAccountUser.getAccountBalance().add(transferRequest.getAmount()));
            String destinationUsername = destinationAccountUser.getFirstName() + " " + destinationAccountUser.getLastName()  +" " + destinationAccountUser.getOtherName();
            userRepository.save(destinationAccountUser);
//
        EmailDetails creditAlert = EmailDetails.builder(). subject("CREDIT ALERT")
                .recipient(destinationAccountUser.getEmail())
                .messageBody("The sum of " + transferRequest.getAmount() + " has been credited to your account and your new account balance is " + destinationAccountUser.getAccountBalance())
                .build();
//        emailService.sendEmailAlert(creditAlert);
        return BankResponse.builder().
                responseCode(AccountUtils.TRANSFER_SUCCESSFUL_CODE)
                .responseMessage(AccountUtils.TRANSFER_SUCCESSFUL_MESSAGE)
                .accountInfo(null).
                build();


    }


}

