package com.bank.api.model;

import com.bank.domain.model.AccountOperation;
import lombok.Builder;

import java.util.List;
import java.util.UUID;
@Builder
public record BankAccountDto(UUID id, Double balance,List<AccountOperation> accountOperations) {

}
