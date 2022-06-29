package Exceptions;

public class InvalidNoticeException extends RuntimeException{
    public InvalidNoticeException() {
        super("The notice You want to create is invalid.");
    }
}
