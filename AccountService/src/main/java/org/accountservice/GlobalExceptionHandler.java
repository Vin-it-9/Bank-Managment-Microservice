package org.accountservice;

import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle FeignException.NotFound (404) specifically
    @ExceptionHandler(FeignException.NotFound.class)
    public ResponseEntity<Map<String, Object>> handleFeignNotFound(FeignException.NotFound ex) {
        Map<String, Object> errorAttributes = new HashMap<>();
        errorAttributes.put("timestamp", LocalDateTime.now());
        errorAttributes.put("status", HttpStatus.NOT_FOUND.value());
        errorAttributes.put("error", HttpStatus.NOT_FOUND.getReasonPhrase());
        // Optionally, you can extract more specific details from ex if needed.
        errorAttributes.put("message", ex.getMessage());
        // You can set the path manually if desired:
        errorAttributes.put("path", "Requested API path");

        return new ResponseEntity<>(errorAttributes, HttpStatus.NOT_FOUND);
    }

    // Handle any FeignException
    @ExceptionHandler(FeignException.class)
    public ResponseEntity<Map<String, Object>> handleFeignException(FeignException ex) {
        Map<String, Object> errorAttributes = new HashMap<>();
        errorAttributes.put("timestamp", LocalDateTime.now());
        errorAttributes.put("status", ex.status());
        errorAttributes.put("error", HttpStatus.valueOf(ex.status()).getReasonPhrase());
        errorAttributes.put("message", ex.getMessage());
        errorAttributes.put("path", "Requested API path");

        return new ResponseEntity<>(errorAttributes, HttpStatus.valueOf(ex.status()));
    }
}
