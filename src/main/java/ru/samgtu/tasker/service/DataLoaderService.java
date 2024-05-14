package ru.samgtu.tasker.service;

import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.samgtu.tasker.api.exception.NotFoundEmployeeException;
import ru.samgtu.tasker.api.exception.NotFoundInventoryException;
import ru.samgtu.tasker.api.exception.NotFoundMachineException;
import ru.samgtu.tasker.entity.*;
import ru.samgtu.tasker.repository.*;
import ru.samgtu.tasker.util.FileUtil;
import org.apache.poi.xssf.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DataLoaderService {
    private final FileUtil fileUtil;
    private final EmployeeRepository employeeRepository;

    private final TimeSpendRepository timeSpendRepository;
    private final MachineRepository machineRepository;
    private final ToolRepository toolRepository;
    private final InventoryRepository inventoryRepository;
    private final TaskRepository taskRepository;
    @Autowired
    public DataLoaderService(FileUtil fileUtil, EmployeeRepository employeeRepository, TimeSpendRepository timeSpendRepository, MachineRepository machineRepository, ToolRepository toolRepository, InventoryRepository inventoryRepository, TaskRepository taskRepository) {
        this.fileUtil = fileUtil;
        this.employeeRepository = employeeRepository;
        this.timeSpendRepository = timeSpendRepository;
        this.machineRepository = machineRepository;
        this.toolRepository = toolRepository;
        this.inventoryRepository = inventoryRepository;
        this.taskRepository = taskRepository;
    }

    public void parseExcel(MultipartFile file)
            throws IOException, NotFoundEmployeeException, NotFoundInventoryException, NotFoundMachineException {
        try {
            File excelFile = fileUtil.convertMultiPartFileToFile(file);

            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(excelFile));

            //парсинг рабочих
            Sheet sheet = workbook.getSheetAt(1);
            EmployeeEntity employee = new EmployeeEntity();
            List<SkillLevel> skillLevelList = new ArrayList<>();
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (!row.getCell(0).getStringCellValue().isEmpty()) {
                    if (!row.getCell(1).getStringCellValue().isEmpty()) {
                        TypeEntity type = new TypeEntity();
                        SkillLevel skillLevel = new SkillLevel();
                        type.setSkill(row.getCell(1).getStringCellValue());
                        skillLevel.setSkill(type);
                        skillLevel.setLevel((int)(row.getCell(2).getNumericCellValue()));
                        skillLevelList.add(skillLevel);
                    }
                    employee.setName(row.getCell(0).getStringCellValue());
                }
                else {
                    if (!row.getCell(1).getStringCellValue().isEmpty()) {
                        TypeEntity type = new TypeEntity();
                        SkillLevel skillLevel = new SkillLevel();
                        type.setSkill(row.getCell(1).getStringCellValue());
                        skillLevel.setSkill(type);
                        skillLevel.setLevel((int)(row.getCell(2).getNumericCellValue()));
                        skillLevelList.add(skillLevel);
                    }
                }
                Row nextRow = sheet.getRow(i+1);
                if (nextRow == null || !nextRow.getCell(0).getStringCellValue().isEmpty()) {
                    employee.setSkillLevelList(skillLevelList);
                    employeeRepository.save(employee);
                    skillLevelList = new ArrayList<>();
                    employee = new EmployeeEntity();
                }
            }

            //парсинг станков (machine)
            sheet = workbook.getSheetAt(2);
            MachineEntity machine = new MachineEntity();
            List<TypeEntity> typeEntityList = new ArrayList<>();
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row.getCell(0).getNumericCellValue() != 0.0f) {
                    if (!row.getCell(1).getStringCellValue().isEmpty()) {
                        TypeEntity type = new TypeEntity();
                        type.setSkill(row.getCell(1).getStringCellValue());
                        typeEntityList.add(type);
                    }
                    machine.setSerialNumber(Double.toString(row.getCell(0).getNumericCellValue()));
                }
                else {
                    if (!row.getCell(1).getStringCellValue().isEmpty()) {
                        TypeEntity type = new TypeEntity();
                        type.setSkill(row.getCell(1).getStringCellValue());
                        typeEntityList.add(type);
                    }
                }
                Row nextRow = sheet.getRow(i+1);
                if (nextRow == null || nextRow.getCell(0).getNumericCellValue() != 0.0f) {
                    machine.setTypes(typeEntityList);
                    machineRepository.save(machine);
                    typeEntityList = new ArrayList<>();
                    machine = new MachineEntity();
                }
            }
            //парсинг инвентаря
            sheet = workbook.getSheetAt(3);
            InventoryEntity currentInventoryEntity = new InventoryEntity();
            List<ToolEntity> toolEntityList = new ArrayList<>();
            List<MachineEntity> machineEntityList = new ArrayList<>();

            for (int i = 2; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);

                if (!row.getCell(0).getStringCellValue().isEmpty()) {
                    if (row.getCell(1).getNumericCellValue() != 0.0f) {
                        machine = machineRepository.findBySerialNumber(Double.toString(row.getCell(1).getNumericCellValue()))
                                .orElseThrow(NotFoundMachineException::new);
                        machine.setInventoryEntity(currentInventoryEntity);
                        machineEntityList.add(machine);
                    }
                    if (!row.getCell(2).getStringCellValue().isEmpty()) {
                        ToolEntity tool = new ToolEntity();
                        tool.setName(row.getCell(2).getStringCellValue());
                        tool.setInventoryEntity(currentInventoryEntity);
                        toolEntityList.add(tool);
                    }

                    currentInventoryEntity.setName(row.getCell(0).getStringCellValue());
                }
                else {
                    if (row.getCell(1).getNumericCellValue() != 0.0f) {
                        machine = machineRepository.findBySerialNumber(Double.toString(row.getCell(1).getNumericCellValue()))
                                .orElseThrow(NotFoundMachineException::new);
                        machine.setInventoryEntity(currentInventoryEntity);
                        machineEntityList.add(machine);
                    }
                    if (!row.getCell(2).getStringCellValue().isEmpty()) {
                        ToolEntity tool = new ToolEntity();
                        tool.setName(row.getCell(2).getStringCellValue());
                        tool.setInventoryEntity(currentInventoryEntity);
                        toolEntityList.add(tool);
                    }
                }

                Row nextRow = sheet.getRow(i+1);
                if (nextRow == null || !nextRow.getCell(0).getStringCellValue().isEmpty()) {
                    currentInventoryEntity.setToolEntityList(toolEntityList);
                    currentInventoryEntity.setMachineEntityList(machineEntityList);
                    inventoryRepository.save(currentInventoryEntity);
                    machineEntityList = new ArrayList<>();
                    toolEntityList = new ArrayList<>();
                    currentInventoryEntity = new InventoryEntity();
                }
            }

            //парсинг задач
            sheet = workbook.getSheetAt(4);
            TaskEntity taskEntity = new TaskEntity();
            skillLevelList = new ArrayList<>();
            for (int i = 2; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);

                if (!row.getCell(0).getStringCellValue().isEmpty()) {
                    if (!row.getCell(2).getStringCellValue().isEmpty()) {
                        employee = employeeRepository.findByName(row.getCell(0).getStringCellValue())
                                .orElseThrow(NotFoundEmployeeException::new);
                        currentInventoryEntity = inventoryRepository.findByName(row.getCell(1).getStringCellValue())
                                .orElseThrow(NotFoundInventoryException::new);
                        SkillLevel skillLevel = new SkillLevel();
                        TypeEntity type = new TypeEntity();
                        type.setSkill(row.getCell(2).getStringCellValue());
                        skillLevel.setSkill(type);
                        skillLevel.setLevel((int)row.getCell(3).getNumericCellValue());
                        taskEntity.setInventoryEntity(currentInventoryEntity);
                        taskEntity.setEmployeeEntity(employee);
                        taskEntity.setTimeStart(LocalDateTime.parse(row.getCell(4).getStringCellValue()));
                        taskEntity.setTimeEnd(LocalDateTime.parse(row.getCell(5).getStringCellValue()));
                        skillLevelList.add(skillLevel);
                    }
                }
                else {
                    if (!row.getCell(2).getStringCellValue().isEmpty()) {
                        SkillLevel skillLevel = new SkillLevel();
                        TypeEntity type = new TypeEntity();
                        type.setSkill(row.getCell(2).getStringCellValue());
                        skillLevel.setSkill(type);
                        skillLevel.setLevel((int)row.getCell(3).getNumericCellValue());
                        skillLevelList.add(skillLevel);
                    }
                }

                Row nextRow = sheet.getRow(i+1);
                if (nextRow == null || !nextRow.getCell(0).getStringCellValue().isEmpty()) {
                    taskEntity.setRequiredSkillLevelList(skillLevelList);
                    taskRepository.save(taskEntity);
                    skillLevelList = new ArrayList<>();
                    taskEntity = new TaskEntity();
                }
            }

            workbook.close();
            Files.delete(excelFile.toPath());
        } catch (IOException e) {
            throw e;
        }
    }
}
