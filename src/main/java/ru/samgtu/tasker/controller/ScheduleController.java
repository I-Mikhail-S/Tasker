package ru.samgtu.tasker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.samgtu.tasker.api.dto.response.ScheduleResponseDTO;
import ru.samgtu.tasker.api.exception.WrongAppointmentTime;
import ru.samgtu.tasker.service.ScheduleService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/schedule")
@CrossOrigin
public class ScheduleController {
    private final ScheduleService scheduleService;
    @Autowired
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

/*    @PostMapping("/create")
    public ResponseEntity<ScheduleResponseDTO> createSchedule() throws WrongAppointmentTime {
        return ResponseEntity.ok(scheduleService.createSchedule());
    }*/

    @PostMapping("/update")
    public ResponseEntity<ScheduleResponseDTO> updateSchedule() throws WrongAppointmentTime {
        return ResponseEntity.ok(scheduleService.updateSchedule());
    }

    @GetMapping()
    public ResponseEntity<List<ScheduleResponseDTO>> findAllSchedules() {
        return ResponseEntity.ok(scheduleService.findAllSchedules());
    }
}
