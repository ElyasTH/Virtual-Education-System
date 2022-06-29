package Exceptions;

public class InvalidContentException extends RuntimeException{
    public InvalidContentException() {
        super("The content You want create is invalid.");
    }
}
