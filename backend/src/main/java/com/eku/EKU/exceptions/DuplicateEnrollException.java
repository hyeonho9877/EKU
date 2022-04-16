package com.eku.EKU.exceptions;

public class DuplicateEnrollException extends RuntimeException{
    @Override
    public String getMessage() {
        return "enrolling duplicate info";
    }
}
