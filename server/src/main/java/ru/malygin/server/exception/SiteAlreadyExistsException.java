package ru.malygin.server.exception;

public class SiteAlreadyExistsException extends Exception{
    public SiteAlreadyExistsException(String message) {
        super(message);
    }
}
