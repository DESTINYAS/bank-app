package com.destiny.the_destiny_bank.services;

import com.destiny.the_destiny_bank.dto.*;
import com.destiny.the_destiny_bank.entity.User;
import com.destiny.the_destiny_bank.repository.TransactionRepository;
import com.destiny.the_destiny_bank.repository.UserRepository;
import com.destiny.the_destiny_bank.utils.AccountUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;
    @Autowired
    TransactionService transactionService;
    @Autowired
    EmailService emailService;
    @Override
    public BankResponse createUser(UserDto userDto) {
        if(userRepository.existsByEmail(userDto.getEmail())){
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        User newUser = User.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .otherName(userDto.getOtherName())
                .gender(userDto.getGender())
                .address(userDto.getAddress())
                .stateOfOrigin(userDto.getStateOfOrigin())
                .accountNumber(AccountUtils.generateAccountNumber())
                .email(userDto.getEmail())
                .accountBalance(BigDecimal.ZERO)
                .phoneNumber(userDto.getPhoneNumber())
                .alternativePhoneNumber(userDto.getAlternativePhoneNumber())
                .status("ACTIVE")
                .build();
        User savedUser = userRepository.save(newUser);
        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(savedUser.getEmail())
                .subject("Account Creation")
                .messageBody("Congratulations, Account created successfully \n Your account details :\n" +
                        "Account name: "+savedUser.getFirstName()+" "+savedUser.getOtherName()+" "+savedUser.getLastName()+
                        "\n Your Account number is :"+savedUser.getAccountNumber())
                .build();
        emailService.sendEmailAlert(emailDetails);
        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_CREATED_CODE)
                .responseMessage(AccountUtils.ACCOUNT_CREATED_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountBalance(String.valueOf(savedUser.getAccountBalance()))
                        .accountName(savedUser.getFirstName()+" "+savedUser.getOtherName()+" "+savedUser.getLastName())
                        .accountNumber(savedUser.getAccountNumber())
                        .build())
                .build();
    }

    @Override
    public BankResponse balanceEnquiry(EnquiryRequest enquiryRequest) {
//        System.out.println(enquiryRequest.getAccountNumber());
        boolean accountExists = userRepository.existsByAccountNumber(enquiryRequest.getAccountNumber());
        if(!accountExists){
            return BankResponse.builder()
                    .responseCode(AccountUtils.NO_ACCOUNT_EXISTS_CODE)
                    .responseMessage(AccountUtils.NO_ACCOUNT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
//        System.out.println("Account :"+accountExists);
        User foundUser = userRepository.findByAccountNumber(enquiryRequest.getAccountNumber());
//        System.out.println(foundUser);
        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_FOUND_CODE)
                .responseMessage(AccountUtils.ACCOUNT_FOUND_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountBalance(String.valueOf(foundUser.getAccountBalance()))
                        .accountName(foundUser.getFirstName()+" "+foundUser.getOtherName()+" "+foundUser.getLastName())
                        .build())
                .build();
    }

    @Override
    public BankResponse creditAccount(CreditDebitDto creditDebitDto) {
        boolean accountExists = userRepository.existsByAccountNumber(creditDebitDto.getAccountNumber());
        if(!accountExists){
            return BankResponse.builder()
                    .responseCode(AccountUtils.NO_ACCOUNT_EXISTS_CODE)
                    .responseMessage(AccountUtils.NO_ACCOUNT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        User userToCredit = userRepository.findByAccountNumber(creditDebitDto.getAccountNumber());
        userToCredit.setAccountBalance(userToCredit.getAccountBalance().add(creditDebitDto.getAmount()));
        userRepository.save(userToCredit);
        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_CREDITED_CODE)
                .responseMessage(AccountUtils.ACCOUNT_CREDITED_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountName(userToCredit.getFirstName()+" "+userToCredit.getOtherName()+" "+userToCredit.getLastName())
                        .accountBalance(String.valueOf(userToCredit.getAccountBalance()))
                        .accountNumber(userToCredit.getAccountNumber())
                        .build())
                .build();
    }

    @Override
    public BankResponse debitAccount(CreditDebitDto creditDebitDto) {
        boolean accountExists = userRepository.existsByAccountNumber(creditDebitDto.getAccountNumber());
        if(!accountExists){
            return BankResponse.builder()
                    .responseCode(AccountUtils.NO_ACCOUNT_EXISTS_CODE)
                    .responseMessage(AccountUtils.NO_ACCOUNT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        User userToDebit = userRepository.findByAccountNumber(creditDebitDto.getAccountNumber());
       if (userToDebit.getAccountBalance().compareTo(creditDebitDto.getAmount())<0){
        return BankResponse.builder()
                .responseCode(AccountUtils.INSUFFICIENT_BALANCE_CODE)
                .responseMessage(AccountUtils.ACCOUNT_INSUFFICIENT_MESSAGE)
                .accountInfo(null)
                .build();
       }

      userToDebit.setAccountBalance(userToDebit.getAccountBalance().subtract(creditDebitDto.getAmount())); ;
       userRepository.save(userToDebit);
//        save Transaction
        TransactionDto transactionDto =TransactionDto.builder()
                .transactionType("Debit")
                .accountNumber(userToDebit.getAccountNumber())
                .amount(creditDebitDto.getAmount())
                .build();
        transactionService.saveTransaction(transactionDto);



        return BankResponse.builder()
               .responseCode(AccountUtils.ACCOUNT_DEBITED_CODE)
               .responseMessage(AccountUtils.ACCOUNT_DEBITED_MESSAGE)
               .accountInfo(AccountInfo.builder()
                       .accountNumber(userToDebit.getAccountNumber())
                       .accountBalance(String.valueOf(userToDebit.getAccountBalance()))
                       .accountName(userToDebit.getFirstName()+" "+userToDebit.getOtherName()+" "+userToDebit.getLastName())
                       .build())
               .build();
    }

    @Override
    @Transactional
    public BankResponse transfer(TransferDto transferDto) {
        boolean fromAccountExists = userRepository.existsByAccountNumber(transferDto.getFromAccountNumber());
        boolean toAccountExists = userRepository.existsByAccountNumber(transferDto.getToAccountNumber());
        if(!fromAccountExists){
            return BankResponse.builder()
                    .responseCode(AccountUtils.NO_ACCOUNT_EXISTS_CODE)
                    .responseMessage(AccountUtils.NO_ACCOUNT_EXISTS_MESSAGE)
                    .accountInfo(AccountInfo.builder()
                            .accountNumber(transferDto.getFromAccountNumber())
                            .build())
                    .build();
        }
        if(!toAccountExists){
            return BankResponse.builder()
                    .responseCode(AccountUtils.NO_ACCOUNT_EXISTS_CODE)
                    .responseMessage(AccountUtils.NO_ACCOUNT_EXISTS_MESSAGE)
                    .accountInfo(AccountInfo.builder()
                            .accountNumber(transferDto.getToAccountNumber())
                            .build())
                    .build();
        }
        User userTransfering = userRepository.findByAccountNumber(transferDto.getFromAccountNumber());
        User userReceiving = userRepository.findByAccountNumber(transferDto.getToAccountNumber());
        if(userTransfering.getAccountBalance().compareTo(transferDto.getAmount())<0){
            return BankResponse.builder()
                    .responseCode(AccountUtils.INSUFFICIENT_BALANCE_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_INSUFFICIENT_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        userTransfering.setAccountBalance(userTransfering.getAccountBalance().subtract(transferDto.getAmount()));

//        save Transaction
        TransactionDto transactionDtot =TransactionDto.builder()
                .transactionType("Debit")
                .accountNumber(userTransfering.getAccountNumber())
                .amount(transferDto.getAmount())
                .build();
        transactionService.saveTransaction(transactionDtot);

        userReceiving.setAccountBalance(userReceiving.getAccountBalance().add(transferDto.getAmount()));

//        save Transaction
        TransactionDto transactionDto =TransactionDto.builder()
                .transactionType("Credit")
                .accountNumber(userReceiving.getAccountNumber())
                .amount(transferDto.getAmount())
                .build();
transactionService.saveTransaction(transactionDto);
        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_DEBITED_CODE)
                .responseMessage(AccountUtils.ACCOUNT_DEBITED_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountNumber(userTransfering.getAccountNumber())
                        .accountBalance(String.valueOf(userTransfering.getAccountBalance()))
                        .build())
                .build();
    }


}
