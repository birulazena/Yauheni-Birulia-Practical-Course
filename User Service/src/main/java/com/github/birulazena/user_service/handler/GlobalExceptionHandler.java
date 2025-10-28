package com.github.birulazena.user_service.handler;

import com.github.birulazena.user_service.exception.CardInfoNotFoundException;
import com.github.birulazena.user_service.exception.DuplicateEmailException;
import com.github.birulazena.user_service.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({UserNotFoundException.class, CardInfoNotFoundException.class})
    public ResponseEntity<String> NotFoundHandler(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> validExceptionHandler(MethodArgumentNotValidException ex) {
        String exceptionMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .collect(Collectors.joining("\n"));
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(exceptionMessage);
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<String> duplicateEmailHandler(DuplicateEmailException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }



}
