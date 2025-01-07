package com.bank.domain.model;

import com.bank.domain.model.enums.OperationType;

import java.time.LocalDateTime;

public record AccountOperation(Double amount, LocalDateTime operationDate, Double balance, OperationType type) {
}
