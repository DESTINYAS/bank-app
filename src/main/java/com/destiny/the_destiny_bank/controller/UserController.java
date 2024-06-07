package com.destiny.the_destiny_bank.controller;

import com.destiny.the_destiny_bank.dto.*;
import com.destiny.the_destiny_bank.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@Tag(name = "USER MANSGEMENT")
public class UserController {
    @Autowired
    UserService userService;
    @Operation(
            summary = "create new user account",
            description = "creating a new user and generating inique 10 digit account number"
    )
    @ApiResponse(
            responseCode = "201",
            description = "http status account created"
    )
    @PostMapping("create")
    public BankResponse createAccount(@RequestBody UserDto userDto){
        return userService.createUser(userDto);
    }

    @Operation(
            summary = "Balance enquiry",
            description = "check balance with 10 digit account number"
    )
    @ApiResponse(
            responseCode = "200",
            description = "http status 200 success"
    )
    @GetMapping("/balance")
    public BankResponse balanceEnquiry(@RequestBody EnquiryRequest enquiryRequest){
        return userService.balanceEnquiry(enquiryRequest);
        }

        @PostMapping("credit")
    public BankResponse creditAnAcount(@RequestBody CreditDebitDto creditDebitDto){
        return  userService.creditAccount(creditDebitDto);
        }


    @PostMapping("debit")
    public BankResponse debitAccount(@RequestBody CreditDebitDto creditDebitDto){
        return  userService.debitAccount(creditDebitDto);
    }


    @PostMapping("transfer")
    public BankResponse transfer(@RequestBody TransferDto transferDto){
        return  userService.transfer(transferDto);
    }
}
