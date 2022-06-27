package Exceptions;

public class StudentAlreadyExistsException extends RuntimeException{
    public StudentAlreadyExistsException() {
        super("Student already Exists.");
    }
}
