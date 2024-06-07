package com.destiny.the_destiny_bank.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransferDto {
    private String fromAccountNumber;
    private String toAccountNumber;

    private BigDecimal amount;
}
