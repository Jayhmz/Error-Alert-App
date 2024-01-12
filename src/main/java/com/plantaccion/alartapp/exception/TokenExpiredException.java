package com.plantaccion.alartapp.exception;

public class TokenExpiredException extends AppRuntimeException {

    public TokenExpiredException(String message) {
        super(message);
    }
}
