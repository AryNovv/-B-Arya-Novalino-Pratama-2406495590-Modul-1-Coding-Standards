package com.example.eshop.enums;

import lombok.Getter;

@Getter
public enum PaymentStatus {
    SUCCESS("SUCCESS"),
    REJECTED("REJECTED"),
    WAITING("WAITING");

    private final String value;

    PaymentStatus(String value) {
        this.value = value;
    }
}
