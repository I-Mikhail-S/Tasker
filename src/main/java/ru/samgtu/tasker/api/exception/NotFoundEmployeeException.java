package ru.samgtu.tasker.api.exception;

public class NotFoundEmployeeException extends Exception {
    public NotFoundEmployeeException() { super("Employee not  found"); }
}
