package ru.malygin.server.exception;

public class SiteNotFoundException extends Exception{
    public SiteNotFoundException(String message) {
        super(message);
    }
}
