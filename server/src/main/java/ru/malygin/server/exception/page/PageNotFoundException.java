package ru.malygin.server.exception.page;

public class PageNotFoundException extends Exception{
    public PageNotFoundException(String message) {
        super(message);
    }
}
