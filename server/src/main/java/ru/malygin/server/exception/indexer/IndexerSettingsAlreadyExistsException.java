package ru.malygin.server.exception.indexer;

public class IndexerSettingsAlreadyExistsException extends Exception{
    public IndexerSettingsAlreadyExistsException(String message) {
        super(message);
    }
}
