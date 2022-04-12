package com.eku.EKU.exceptions;

/**
 * 해당 학생이 db에 없는 경우 발생하는 Exception
 */
public class NoSuchStudentException extends RuntimeException{
    @Override
    public String getMessage() {
        return "Cannot find such Student";
    }
}
