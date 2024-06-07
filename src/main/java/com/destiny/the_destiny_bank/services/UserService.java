package com.destiny.the_destiny_bank.services;

import com.destiny.the_destiny_bank.dto.*;

public interface UserService {
    BankResponse createUser(UserDto userDto);
    BankResponse balanceEnquiry(EnquiryRequest enquiryRequest);

    BankResponse creditAccount(CreditDebitDto creditDebitDto);
    BankResponse debitAccount(CreditDebitDto creditDebitDto);

    BankResponse transfer(TransferDto transferDto);
}
