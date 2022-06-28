package Exceptions;

public class InvalidQuestionTypeException extends RuntimeException{
    public InvalidQuestionTypeException() {
        super("This question type doesn't exist");
    }
}
