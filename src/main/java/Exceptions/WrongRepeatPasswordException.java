package Exceptions;

public class WrongRepeatPasswordException extends RuntimeException{

    public WrongRepeatPasswordException(){
        super("You enterd your password incorrectly.");
    }
}
