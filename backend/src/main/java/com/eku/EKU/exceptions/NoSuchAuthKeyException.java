package com.eku.EKU.exceptions;

import java.util.NoSuchElementException;

/**
 * 이메일 인증 키가 존재하지 않는 경우 발생하는 Exception
 */
public class NoSuchAuthKeyException extends NoSuchElementException {
    @Override
    public String getMessage() {
        return "No such AuthKey Exception Thrown";
    }
}
