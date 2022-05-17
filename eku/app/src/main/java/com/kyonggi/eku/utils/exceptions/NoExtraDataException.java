package com.kyonggi.eku.utils.exceptions;

import androidx.annotation.Nullable;

public class NoExtraDataException extends RuntimeException{
    @Nullable
    @Override
    public String getMessage() {
        return "No Extra Data Exception Has Thrown";
    }
}
