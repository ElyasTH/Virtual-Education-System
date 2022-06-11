package Exceptions;

public class InvalidUsernameException extends RuntimeException{

    public InvalidUsernameException() {
        super("Your username entry is invalid.");
    }

}
