package com.destiny.the_destiny_bank.repository;

import com.destiny.the_destiny_bank.dto.BankResponse;
import com.destiny.the_destiny_bank.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    Boolean existsByEmail(String email);
    Boolean existsByAccountNumber(String accountNumber);
    Boolean existsByPhoneNumber(String phoneNumber);

    User findByAccountNumber(String accountNumber);
}
