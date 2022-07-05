package Exceptions;

public class ItemNotFoundInDatabaseException extends RuntimeException{
    public ItemNotFoundInDatabaseException() {
        super("This item doesn't exist in database.");
    }
}
