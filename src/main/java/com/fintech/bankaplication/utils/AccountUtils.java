package com.fintech.bankaplication.utils;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Random;

public class AccountUtils {
    public  static  final String ACCOUNT_EXISTS_CODE ="001";
    public  static  final String ACCOUNT_NOT_EXIST_CODE ="003";
    public  static  final String ACCOUNT_CREDITED_SUCCESS ="005";
    public  static  final String ACCOUNT_FOUND_CODE ="004";
    public  static  final String ACCOUNT_FOUND_SUCCESS ="Account found successfully";


    public  static  final String ACCOUNT_EXITS_MESSAGE ="This user already exists";
    public  static  final String ACCOUNT_NOT_EXIT_MESSAGE ="This user does not exist";
    public  static  final String ACCOUNT_CREATION_SUCCESS = "002";
    public  static  final String ACCOUNT_CREATION_MESSAGE = "Account has been created successfully";
    public  static  final String INSUFICIENT_BALANCE_CODE = "006";
    public  static  final String INSUFICIENT_BALANCE_MESSAGE = "Insufficient Funds";
    public  static  final String ACCOUNT_DEBITED_SUCCESS = "007";
    public  static  final String ACCOUNT_DEBITED_MESSAGE = "Account has been debited successfully";
    public static  final  String TRANSFER_SUCCESSFUL_CODE = "008";
    public static  final  String TRANSFER_SUCCESSFUL_MESSAGE = "Transfer Successful";





    public static String generateAccountNumber() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        String yearString = Integer.toString(currentYear);

        Random random = new Random();
        int randomDigits = random.nextInt(900000) + 100000;
        String randomNumberString = Integer.toString(randomDigits);

        return yearString + randomNumberString;
    }

}
