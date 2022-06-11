package Exceptions;

public class InvalidLastnameException extends RuntimeException{

    public InvalidLastnameException() {
        super("Your lastname entry is invalid.");
    }

}
