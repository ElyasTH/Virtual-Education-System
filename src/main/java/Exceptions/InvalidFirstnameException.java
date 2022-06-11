package Exceptions;

public class InvalidFirstnameException extends RuntimeException{

    public InvalidFirstnameException() {
        super("Your firstname entry is invalid.");
    }

}
