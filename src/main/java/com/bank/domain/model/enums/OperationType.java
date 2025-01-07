package com.bank.domain.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OperationType {
    DEPOSIT("Dépôt"),
    WITHDRAWAL("Retrait");

    private final String label;

}
