package com.company.library.exceptions;

import javax.servlet.ServletException;

public class AuthorizationException extends ServletException {
    public AuthorizationException(String message) {
        super(message);
    }
}
