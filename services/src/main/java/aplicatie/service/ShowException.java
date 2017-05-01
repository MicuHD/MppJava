package aplicatie.service;

public class ShowException extends Exception{
    public ShowException() {
    }

    public ShowException(String message) {
        super(message);
    }

    public ShowException(String message, Throwable cause) {
        super(message, cause);
    }
}