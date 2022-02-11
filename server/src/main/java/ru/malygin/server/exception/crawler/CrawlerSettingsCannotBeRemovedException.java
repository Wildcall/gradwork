package ru.malygin.server.exception.crawler;

public class CrawlerSettingsCannotBeRemovedException extends Exception{
    public CrawlerSettingsCannotBeRemovedException(String message) {
        super(message);
    }
}
