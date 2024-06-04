package com.destiny.the_destiny_bank.controller;

import com.destiny.the_destiny_bank.dto.BankResponse;
import com.destiny.the_destiny_bank.dto.UserDto;
import com.destiny.the_destiny_bank.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;
    @PostMapping("create")
    public BankResponse createAccount(@RequestBody UserDto userDto){
        return userService.createUser(userDto);
    }
}
