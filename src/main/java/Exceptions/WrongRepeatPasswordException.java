package Exceptions;

public class WrongRepeatPasswordException extends RuntimeException{

    public WrongRepeatPasswordException(){
        super("Passwords do not match");
    }
}
