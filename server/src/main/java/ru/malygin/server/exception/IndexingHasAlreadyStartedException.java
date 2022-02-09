package ru.malygin.server.exception;

public class IndexingHasAlreadyStartedException extends Exception{
    public IndexingHasAlreadyStartedException(String message) {
        super(message);
    }
}
