package Exceptions;

public class InvalidContentIdException extends RuntimeException{
    public InvalidContentIdException() {
        super("This content doesn't exist");
    }
}
