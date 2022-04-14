package com.eku.EKU.exceptions;

import java.util.NoSuchElementException;

/**
 * 해당 학생이 db에 없는 경우 발생하는 Exception
 */
public class NoSuchStudentException extends NoSuchElementException {
    @Override
    public String getMessage() {
        return "No such Student Exception Thrown";
    }
}
