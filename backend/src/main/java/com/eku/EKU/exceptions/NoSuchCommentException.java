package com.eku.EKU.exceptions;

import java.util.NoSuchElementException;

public class NoSuchCommentException extends NoSuchElementException {
    @Override
    public String getMessage() {
        return "No such Comment Exception Thrown";
    }
}
