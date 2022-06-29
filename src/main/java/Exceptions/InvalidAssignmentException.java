package Exceptions;

public class InvalidAssignmentException extends RuntimeException{
    public InvalidAssignmentException() {
        super("The assignment You want create is invalid.");
    }
}
