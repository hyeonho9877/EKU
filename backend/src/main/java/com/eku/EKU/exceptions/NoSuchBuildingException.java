package com.eku.EKU.exceptions;

import java.util.NoSuchElementException;

public class NoSuchBuildingException extends NoSuchElementException {
    @Override
    public String getMessage() {
        return "No such Building Exception Thrown";
    }
}
