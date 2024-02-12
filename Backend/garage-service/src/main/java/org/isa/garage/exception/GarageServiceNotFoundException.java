package org.isa.garage.exception;

public class GarageServiceNotFoundException extends RuntimeException{
    public GarageServiceNotFoundException(String message) {
        super(message);
    }
}
