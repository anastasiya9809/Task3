package com.epam.taxi;

public class TaxiException extends Exception {

    public TaxiException(String message, Exception e) {
        super(message, e);
    }
}
