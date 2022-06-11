package Exceptions;

public class InvalidPhoneNumberException extends RuntimeException{

    public InvalidPhoneNumberException() {
        super("You entered your phone number incorrectly.");
    }
}
