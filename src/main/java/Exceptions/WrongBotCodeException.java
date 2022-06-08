package Exceptions;

public class WrongBotCodeException extends RuntimeException{

    public WrongBotCodeException(){
        super("You entered the code incorrectly.");
    }
}
