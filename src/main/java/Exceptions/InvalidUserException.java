package Exceptions;

public class InvalidUserException extends RuntimeException{
    public InvalidUserException() {
        super("This user doesn't exist.");
    }
}
