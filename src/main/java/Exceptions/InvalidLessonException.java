package Exceptions;

public class InvalidLessonException extends RuntimeException{
    public InvalidLessonException() {
        super("The lesson You want to create is invalid.");
    }
}
