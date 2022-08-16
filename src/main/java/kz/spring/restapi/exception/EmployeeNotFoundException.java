package kz.spring.restapi.exception;

public class EmployeeNotFoundException extends Throwable {
    public EmployeeNotFoundException(String message) {
    super(message);
    }
}
