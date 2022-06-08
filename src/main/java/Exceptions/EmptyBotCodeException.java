package Exceptions;

public class EmptyBotCodeException extends RuntimeException{

    public EmptyBotCodeException(){
        super("The code box is empty.");
    }
}
