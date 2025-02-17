package org.accountservice;

import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import org.accountservice.exception.AccountNotFoundException;
import org.accountservice.exception.UnauthorizedAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleAccountNotFoundException(
            AccountNotFoundException ex, HttpServletRequest request) {

        Map<String, Object> errorAttributes = new HashMap<>();
        errorAttributes.put("status", HttpStatus.NOT_FOUND.value());
        errorAttributes.put("message", ex.getMessage());
        errorAttributes.put("path", request.getRequestURI());

        return new ResponseEntity<>(errorAttributes, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<Map<String, Object>> handleUnauthorizedAccessException(
            UnauthorizedAccessException ex, HttpServletRequest request) {

        Map<String, Object> errorAttributes = new HashMap<>();
        errorAttributes.put("status", HttpStatus.FORBIDDEN.value());
        errorAttributes.put("message", ex.getMessage());
        errorAttributes.put("path", request.getRequestURI());

        return new ResponseEntity<>(errorAttributes, HttpStatus.FORBIDDEN);
    }


    @ExceptionHandler(FeignException.NotFound.class)
    public ResponseEntity<Map<String, Object>> handleFeignNotFound(FeignException.NotFound ex) {
        Map<String, Object> errorAttributes = new HashMap<>();

        errorAttributes.put("status", HttpStatus.NOT_FOUND.value());
        errorAttributes.put("error", HttpStatus.NOT_FOUND.getReasonPhrase());
        errorAttributes.put("message", ex.getMessage());
        errorAttributes.put("path", "Requested API path");

        return new ResponseEntity<>(errorAttributes, HttpStatus.NOT_FOUND);
    }

    // Handle any FeignException
    @ExceptionHandler(FeignException.class)
    public ResponseEntity<Map<String, Object>> handleFeignException(FeignException ex) {
        Map<String, Object> errorAttributes = new HashMap<>();
        errorAttributes.put("status", ex.status());
        errorAttributes.put("error", HttpStatus.valueOf(ex.status()).getReasonPhrase());
        errorAttributes.put("message", ex.getMessage());
        errorAttributes.put("path", "Requested API path");

        return new ResponseEntity<>(errorAttributes, HttpStatus.valueOf(ex.status()));
    }



}
