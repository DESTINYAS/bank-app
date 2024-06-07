package com.destiny.the_destiny_bank.services;

import com.destiny.the_destiny_bank.dto.TransactionDto;
import com.destiny.the_destiny_bank.entity.Transaction;

public interface TransactionService {
    void saveTransaction(TransactionDto transactionDto);
}
