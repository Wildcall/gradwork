package ru.malygin.server.exception;

public class CrawlerSettingsNotFoundException extends Exception{
    public CrawlerSettingsNotFoundException(String message) {
        super(message);
    }
}
