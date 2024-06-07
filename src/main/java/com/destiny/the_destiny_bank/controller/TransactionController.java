package com.destiny.the_destiny_bank.controller;
import com.destiny.the_destiny_bank.entity.Transaction;
import com.destiny.the_destiny_bank.services.BankStatement;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bankstatement")
@AllArgsConstructor
public class TransactionController {
    private BankStatement bankStatement;
    @GetMapping
    public List<Transaction> generateBankStatement(@RequestParam String accountNumber,
                                                   @RequestParam String startDate,
                                                   @RequestParam String endDate){
        return bankStatement.generateStatement(accountNumber,startDate,endDate);
    }
}
