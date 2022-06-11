package Exceptions;

public class InvalidIdException extends  RuntimeException{

    public InvalidIdException(){
        super("Your ID entry is invalid.");
    }
}
