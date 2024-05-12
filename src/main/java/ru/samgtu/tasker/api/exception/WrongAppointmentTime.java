package ru.samgtu.tasker.api.exception;

public class WrongAppointmentTime extends Exception {
    public WrongAppointmentTime() {
        super("Wrong appointment time!");
    }

}
