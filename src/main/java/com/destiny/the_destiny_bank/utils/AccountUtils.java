package com.destiny.the_destiny_bank.utils;

import java.security.SecureRandom;
import java.time.Year;

public class AccountUtils {

    public static final String  ACCOUNT_EXISTS_CODE = "001";
    public static final String  ACCOUNT_EXISTS_MESSAGE = "User Already has an account";

    public static final String  ACCOUNT_CREATED_CODE = "002";
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
