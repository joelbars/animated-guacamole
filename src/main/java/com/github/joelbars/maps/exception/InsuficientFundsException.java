package com.github.joelbars.maps.exception;

public class InsuficientFundsException extends RuntimeException {

    public InsuficientFundsException() {
        super("Saldo insuficiente");
    }
}
