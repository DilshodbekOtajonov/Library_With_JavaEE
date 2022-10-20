package com.company.library.exceptions;

import javax.servlet.ServletException;

public class AuthenticationException extends ServletException {
    public AuthenticationException(String message) {
        super(message);
    }
}