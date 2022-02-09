package ru.malygin.server.exception;

public class CrawlerSettingsAlreadyExistsException extends Exception{
    public CrawlerSettingsAlreadyExistsException(String message) {
        super(message);
    }
}
