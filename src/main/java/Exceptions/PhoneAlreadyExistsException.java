package Exceptions;

public class PhoneAlreadyExistsException extends RuntimeException{
    public PhoneAlreadyExistsException() {
        super("This phone number already exists.");
    }
}
