package com.destiny.the_destiny_bank.repository;

import com.destiny.the_destiny_bank.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, String> {

}
