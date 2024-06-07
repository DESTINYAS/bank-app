package com.destiny.the_destiny_bank.services;

import com.destiny.the_destiny_bank.dto.TransactionDto;
import com.destiny.the_destiny_bank.entity.Transaction;
import com.destiny.the_destiny_bank.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionServiceImpl implements TransactionService{
    @Autowired
    TransactionRepository transactionRepository;
    @Override
    public void saveTransaction(TransactionDto transactionDto) {

    Transaction transaction = Transaction.builder()
            .transactionType(transactionDto.getTransactionType())
            .accountNumber(transactionDto.getAccountNumber())
            .amount(transactionDto.getAmount())
            .status("SUCCESS")
            .build();
    transactionRepository.save(transaction);
        System.out.println("Transaction Saved");
    }
}
