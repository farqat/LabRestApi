package kz.spring.restapi.exception;

public class EmailExists extends Exception{
    public EmailExists(String message) {
        super(message);
    }
}
