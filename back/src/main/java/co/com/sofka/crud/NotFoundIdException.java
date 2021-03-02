package co.com.sofka.crud;

public class NotFoundIdException extends RuntimeException {
    public NotFoundIdException(String message){
        super(message);
    }
}