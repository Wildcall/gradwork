package ru.malygin.server.exception.site;

public class SiteNotFoundException extends Exception{
    public SiteNotFoundException(String message) {
        super(message);
    }
}
