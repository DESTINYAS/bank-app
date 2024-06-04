package com.destiny.the_destiny_bank.services;

import com.destiny.the_destiny_bank.dto.BankResponse;
import com.destiny.the_destiny_bank.dto.UserDto;

public interface UserService {
    BankResponse createUser(UserDto userDto);
}
