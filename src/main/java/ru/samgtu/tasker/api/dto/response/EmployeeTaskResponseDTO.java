package ru.samgtu.tasker.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeTaskResponseDTO {
    private Long id;
    private String employeeName;
    private String inventoryName;
    private LocalDateTime timeStart;
    private LocalDateTime timeEnd;
    private int priority; // 1 - min; 2 - norm; 3 - high
}
