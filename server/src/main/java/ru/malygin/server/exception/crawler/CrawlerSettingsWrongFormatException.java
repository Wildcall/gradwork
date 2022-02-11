package ru.malygin.server.exception.crawler;

public class CrawlerSettingsWrongFormatException extends Exception{
    public CrawlerSettingsWrongFormatException(String message) {
        super(message);
    }
}
