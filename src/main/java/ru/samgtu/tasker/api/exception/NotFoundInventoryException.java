package ru.samgtu.tasker.api.exception;

public class NotFoundInventoryException extends Exception {
    public NotFoundInventoryException() { super("Inventory not found"); }
}
