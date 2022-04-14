package com.eku.EKU.exceptions;

import java.util.NoSuchElementException;

public class NoSuchArticleException extends NoSuchElementException {
    @Override
    public String getMessage() {
        return "No such Element Exception Thrown";
    }
}
