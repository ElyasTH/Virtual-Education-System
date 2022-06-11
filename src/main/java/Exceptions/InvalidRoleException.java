package Exceptions;

public class InvalidRoleException extends RuntimeException{

    public InvalidRoleException() {
        super("You did not choose your role.");
    }
}
