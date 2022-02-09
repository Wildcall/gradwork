package ru.malygin.server.exception;

public class PageNotFoundException extends Exception{
    public PageNotFoundException(String message) {
        super(message);
    }
}
