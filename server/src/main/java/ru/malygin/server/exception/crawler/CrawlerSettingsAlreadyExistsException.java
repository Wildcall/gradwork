package ru.malygin.server.exception.crawler;

public class CrawlerSettingsAlreadyExistsException extends Exception{
    public CrawlerSettingsAlreadyExistsException(String message) {
        super(message);
    }
}
