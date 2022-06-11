package Exceptions;

public class InvalidPasswordException extends RuntimeException{

    public InvalidPasswordException() {
        super("Your Password entry is invalid.");
    }

}
