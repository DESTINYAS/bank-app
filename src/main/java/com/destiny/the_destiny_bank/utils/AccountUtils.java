package com.destiny.the_destiny_bank.utils;

import java.security.SecureRandom;
import java.time.Year;

public class AccountUtils {

    public static final String  ACCOUNT_EXISTS_CODE = "001";

    public static final String  NO_ACCOUNT_EXISTS_CODE = "003";
    public static final String  ACCOUNT_FOUND_CODE = "004";
    public static final String  ACCOUNT_CREDITED_CODE = "005";
    public static final String  INSUFFICIENT_BALANCE_CODE = "006";
    public static final String  ACCOUNT_DEBITED_CODE = "007";

    public static final String  ACCOUNT_CREATED_CODE = "002";
    public static final String  ACCOUNT_EXISTS_MESSAGE = "User Already has an account";
    public static final String  NO_ACCOUNT_EXISTS_MESSAGE = "Account Does Not Exists";

    public static final String  ACCOUNT_FOUND_MESSAGE = "Account Found";
    public static final String  ACCOUNT_INSUFFICIENT_MESSAGE = "INSUFFICIENT BALANCE";
    public static final String  ACCOUNT_DEBITED_MESSAGE = "Account Debited Successfully";

    public static final String  ACCOUNT_CREDITED_MESSAGE = "Account Credited Successfully";

    public static final String  ACCOUNT_CREATED_MESSAGE = "Account created successfully";

    public static String generateAccountNumber() {
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder accountNumber = new StringBuilder();

        // Get the current year and append to the account number
        int currentYear = Year.now().getValue();
        accountNumber.append(currentYear);

        // Generate 6 more digits to make the total length 10
        for (int i = 0; i < 6; i++) {
            int digit = secureRandom.nextInt(10); // Generates a random digit between 0 and 9
            accountNumber.append(digit);
        }

        return accountNumber.toString();
    }

}
