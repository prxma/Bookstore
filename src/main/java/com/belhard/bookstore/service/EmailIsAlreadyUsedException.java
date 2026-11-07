package com.belhard.bookstore.service;

public class EmailIsAlreadyUsedException extends RuntimeException {

    public EmailIsAlreadyUsedException (String email) {
        super("Email is already exist" + email);
    }
}
