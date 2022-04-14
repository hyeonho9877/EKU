package com.eku.EKU.exceptions;

import java.util.NoSuchElementException;

public class NoSuchBoardException extends NoSuchElementException {
    @Override
    public String getMessage() {
        return "No such Board Exception Thrown";
    }
}
