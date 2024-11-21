package com.flowpay.core.exceptions;

public class EmptyTextException extends RuntimeException {
    public EmptyTextException(String field) {
        super(field + " é obrigatório.");
    }
}
