package com.plantaccion.alartapp.exception;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GenericExceptionHandler {

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, Object> handleDuplicateEntryException(DataIntegrityViolationException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("exception name", "DataIntegrityViolationException");
        errorResponse.put("message", ex.getMessage());
        errorResponse.put("error_message", "Duplicate data entry");
        return errorResponse;
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleFieldValueException(IllegalArgumentException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("exception name", "IllegalArgumentException");
        errorResponse.put("message", ex.getMessage());
        return errorResponse;
    }

    @ExceptionHandler(value = BadCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleBadCredentialsException(BadCredentialsException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("exception name", "BadCredentialsException");
        errorResponse.put("message", ex.getMessage());
        return errorResponse;
    }

//    @ExceptionHandler(value = NullPointerException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public Map<String, Object> handleNullPointerException(NullPointerException ex) {
//        Map<String, Object> errorResponse = new HashMap<>();
//        errorResponse.put("exception name", "NullPointerException");
//        errorResponse.put("message", ex.getMessage());
//        errorResponse.put("source", ex.getCause());
//        errorResponse.put("error_message", "Incorrect data entry");
//        return errorResponse;
//    }
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("exception name", "HttpMessageNotReadableException");
        errorResponse.put("message", ex.getMessage());
        return errorResponse;
    }
    @ExceptionHandler(value = TokenExpiredException.class)
    @ResponseStatus(HttpStatus.GATEWAY_TIMEOUT)
    public Map<String, Object> handleExpiredJwtException(TokenExpiredException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("exception name", "TokenExpiredException");
        errorResponse.put("message", ex.getMessage());
        return errorResponse;
    }
//    @ExceptionHandler(value = AccessDeniedException.class)
//    @ResponseStatus(HttpStatus.GATEWAY_TIMEOUT)
//    public Map<String, Object> handleAccessDeniedException(AccessDeniedException ex) {
//        Map<String, Object> errorResponse = new HashMap<>();
//        errorResponse.put("exception name", "AccessDeniedException");
//        errorResponse.put("message", ex.getMessage());
//        return errorResponse;
//    }
}
