package ru.malygin.server.exception;

public class CrawlerSettingsWrongFormatException extends Exception{
    public CrawlerSettingsWrongFormatException(String message) {
        super(message);
    }
}
