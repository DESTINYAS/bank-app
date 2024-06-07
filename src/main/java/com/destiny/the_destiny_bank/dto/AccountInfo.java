package com.destiny.the_destiny_bank.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountInfo {
    @Schema(
            name = "Account name"
    )
    private String accountName;
    @Schema(
            name = "Account balance"
    )
    private String accountBalance;
    @Schema(
            name = "Account number"
    )
    private String accountNumber;
}
