package ru.samgtu.tasker.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.samgtu.tasker.api.dto.response.ErrorResponseDTO;
import ru.samgtu.tasker.api.exception.WrongAppointmentTime;

@ControllerAdvice
public class MainExceptionHandler {
    @ExceptionHandler(WrongAppointmentTime.class)
    public ResponseEntity<ErrorResponseDTO> handleWrongAppointmentTime(WrongAppointmentTime e) {
        return ResponseEntity.badRequest().body(new ErrorResponseDTO(e.getMessage()));
    }
}
