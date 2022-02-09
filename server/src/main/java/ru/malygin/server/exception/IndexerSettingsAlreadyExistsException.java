package ru.malygin.server.exception;

public class IndexerSettingsAlreadyExistsException extends Exception{
    public IndexerSettingsAlreadyExistsException(String message) {
        super(message);
    }
}
