package com.blogging.PJBlogging.exceptions;

public class SpringPJBloggingException extends RuntimeException {
    public SpringPJBloggingException(String s) {
        super(s + " - Runtime Exception");
    }
}
