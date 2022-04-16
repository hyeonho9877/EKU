package com.eku.EKU.exceptions;

public class NoAuthExceptions extends RuntimeException{
    @Override
    public String getMessage() {
        return "This student didn't auth email";
    }
}
