package ru.malygin.server.exception;

public class CrawlerSettingsCannotBeRemovedException extends Exception{
    public CrawlerSettingsCannotBeRemovedException(String message) {
        super(message);
    }
}
