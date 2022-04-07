package com.eku.eku_ocr_test.exceptions;

/**
 * 이메일 인증 키가 존재하지 않는 경우 발생하는 Exception
 */
public class NoSuchAuthKeyException extends RuntimeException{
    @Override
    public String getMessage() {
        return "Cannot find Key from Param";
    }
}
