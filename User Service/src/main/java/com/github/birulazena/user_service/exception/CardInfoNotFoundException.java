package com.github.birulazena.user_service.exception;

public class CardInfoNotFoundException extends RuntimeException {

    public CardInfoNotFoundException() {}

    public CardInfoNotFoundException(String message) {
        super(message);
    }
}
