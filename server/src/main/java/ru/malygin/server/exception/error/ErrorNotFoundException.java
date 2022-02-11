package ru.malygin.server.exception.error;

public class ErrorNotFoundException extends Exception{
    public ErrorNotFoundException(String message) {
        super(message);
    }
}
