package ru.samgtu.tasker.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.samgtu.tasker.api.dto.request.EmployeeCreateRequestDTO;
import ru.samgtu.tasker.api.dto.response.EmployeeResponseDTO;
import ru.samgtu.tasker.api.dto.response.ScheduleResponseDTO;
import ru.samgtu.tasker.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employee")
@CrossOrigin
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping()
    public ResponseEntity<List<EmployeeResponseDTO>> findAllEmployees() {
        return ResponseEntity.ok(employeeService.findAllEmployees());
    }
    @PostMapping()
    public ResponseEntity<EmployeeResponseDTO> createEmployee(@RequestBody EmployeeCreateRequestDTO requestDTO) {
        return ResponseEntity.ok(employeeService.createEmployee(requestDTO));
    }
}
