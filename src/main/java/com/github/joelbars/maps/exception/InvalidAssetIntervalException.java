package com.github.joelbars.maps.exception;

public class InvalidAssetIntervalException extends RuntimeException {

    public InvalidAssetIntervalException() {
        super("Data de vencimento não pode ser anterior a data de emissão");
    }
}
