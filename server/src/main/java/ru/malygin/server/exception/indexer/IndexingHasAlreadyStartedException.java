package ru.malygin.server.exception.indexer;

public class IndexingHasAlreadyStartedException extends Exception{
    public IndexingHasAlreadyStartedException(String message) {
        super(message);
    }
}
