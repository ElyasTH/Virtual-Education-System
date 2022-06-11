package Exceptions;

public class InvalidLoginPasswordException extends RuntimeException{
    public InvalidLoginPasswordException() {
        super("Your password is incorrect.");
    }
}
