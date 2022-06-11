package Exceptions;

public class IdAlreadyExistsException extends RuntimeException{
    public IdAlreadyExistsException() {
        super("This ID already exists.");
    }
}
