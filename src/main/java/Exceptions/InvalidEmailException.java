package Exceptions;

public class InvalidEmailException extends RuntimeException{

    public InvalidEmailException(){
        super("Your email entry is invalid.");
    }
}
