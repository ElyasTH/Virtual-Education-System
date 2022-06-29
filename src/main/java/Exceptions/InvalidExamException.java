package Exceptions;

public class InvalidExamException extends RuntimeException{
    public InvalidExamException() {
        super("The Exam You want create is invalid.");
    }
}
