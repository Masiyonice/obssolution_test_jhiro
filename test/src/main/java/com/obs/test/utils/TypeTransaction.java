package com.obs.test.utils;

public enum TypeTransaction {
    T("Top Up"),
    W("Withdraw");
    private final String description;
    TypeTransaction(String description) {
        this.description = description;
    }
}
