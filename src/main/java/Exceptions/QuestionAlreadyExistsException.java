package Exceptions;

public class QuestionAlreadyExistsException extends RuntimeException{
    public QuestionAlreadyExistsException() {
        super("The question You want create already exists.");
    }
}
