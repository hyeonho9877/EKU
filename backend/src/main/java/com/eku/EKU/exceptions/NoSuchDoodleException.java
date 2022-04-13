package com.eku.EKU.exceptions;

import java.util.NoSuchElementException;

public class NoSuchDoodleException extends NoSuchElementException {
    @Override
    public String getMessage() {
        return "No such Doodle Exception Thrown";
    }
}
