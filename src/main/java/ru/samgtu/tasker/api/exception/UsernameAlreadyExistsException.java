package ru.samgtu.tasker.api.exception;

public class UsernameAlreadyExistsException extends Exception{
    public UsernameAlreadyExistsException() { super("Username already exist");}
}
