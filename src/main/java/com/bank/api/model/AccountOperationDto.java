package com.bank.api.model;

import com.bank.domain.model.enums.OperationType;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record AccountOperationDto(Double amount, LocalDateTime operationDate, Double balance, OperationType type) {
}
