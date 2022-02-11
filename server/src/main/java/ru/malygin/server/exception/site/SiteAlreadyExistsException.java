package ru.malygin.server.exception.site;

public class SiteAlreadyExistsException extends Exception{
    public SiteAlreadyExistsException(String message) {
        super(message);
    }
}
