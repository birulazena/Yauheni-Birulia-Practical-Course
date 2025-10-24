package com.github.birulazena.user_service.exception;

public class DuplicateEmailException extends RuntimeException {

    public DuplicateEmailException() {}

    public DuplicateEmailException(String message) {
        super(message);
    }
}
