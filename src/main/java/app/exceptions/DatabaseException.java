package app.exceptions;

public class DatabaseException extends Exception{
    public Exception ex;
    public String message;

    public DatabaseException(Exception ex, String message){
        this.ex = ex;
        this.message = message;
    }
}
