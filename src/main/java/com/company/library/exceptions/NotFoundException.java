package com.company.library.exceptions;

import javax.servlet.ServletException;

public class NotFoundException extends ServletException {
    public NotFoundException(String message) {
        super(message);
    }


}
