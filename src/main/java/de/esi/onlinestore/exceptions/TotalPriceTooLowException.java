package de.esi.onlinestore.exceptions;

public class TotalPriceTooLowException extends Exception {
    private static final long serialVersionUID = 1L;

    public TotalPriceTooLowException(String message) {
        super(message);
    }
}
