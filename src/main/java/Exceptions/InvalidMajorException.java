package Exceptions;

public class InvalidMajorException extends RuntimeException{

    public InvalidMajorException() {
        super("Your major entry is invalid.");
    }
}
