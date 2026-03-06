package com.roncalho.inventory_control.exceptions;

public class QuantidadeIndisponivelException extends RuntimeException {
    public QuantidadeIndisponivelException(String message) {
        super(message);
    }
}
