package ru.samgtu.tasker.service;

import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.samgtu.tasker.api.exception.NotFoundEmployeeException;
import ru.samgtu.tasker.api.exception.NotFoundInventoryException;
import ru.samgtu.tasker.entity.*;
import ru.samgtu.tasker.repository.*;
import ru.samgtu.tasker.util.FileUtil;
import org.apache.poi.xssf.usermodel.*;

import javax.tools.Tool;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class DataLoaderService {
    private final FileUtil fileUtil;
    private final EmployeeRepository employeeRepository;
    private final TaskEmployeeRepository taskEmployeeRepository;
    private final TaskInventoryRepository taskInventoryRepository;
    private final TimeSpendRepository timeSpendRepository;
    private final MachineRepository machineRepository;
    private final ToolRepository toolRepository;
    private final InventoryRepository inventoryRepository;
    @Autowired
    public DataLoaderService(FileUtil fileUtil, EmployeeRepository employeeRepository, TaskEmployeeRepository taskEmployeeRepository, TaskInventoryRepository taskInventoryRepository, TimeSpendRepository timeSpendRepository, MachineRepository machineRepository, ToolRepository toolRepository, InventoryRepository inventoryRepository) {
        this.fileUtil = fileUtil;
        this.employeeRepository = employeeRepository;
        this.taskEmployeeRepository = taskEmployeeRepository;
        this.taskInventoryRepository = taskInventoryRepository;
        this.timeSpendRepository = timeSpendRepository;
        this.machineRepository = machineRepository;
        this.toolRepository = toolRepository;
        this.inventoryRepository = inventoryRepository;
    }

    public void parseExcel(MultipartFile file)
            throws IOException, NotFoundEmployeeException, NotFoundInventoryException {
        try {
            File excelFile = fileUtil.convertMultiPartFileToFile(file);

            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(excelFile));

            //парсинг рабочих
            Sheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                EmployeeEntity employee = new EmployeeEntity();
                employee.setName(row.getCell(0).getStringCellValue());

                employeeRepository.save(employee);
                if (sheet.getRow(i+1).getCell(0).getStringCellValue().isEmpty()){
                    break;
                }
            }

            //парсинг инвентаря
            sheet = workbook.getSheetAt(1);
            InventoryEntity currentInventoryEntity = new InventoryEntity();
            List<ToolEntity> toolEntityList = new ArrayList<>();
            List<MachineEntity> machineEntityList = new ArrayList<>();

            for (int i = 2; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);

                if (!row.getCell(0).getStringCellValue().isEmpty()) {
                    if (row.getCell(1).getNumericCellValue() != 0.0f) {
                        MachineEntity machine = new MachineEntity();
                        machine.setSerialNumber(Double.toString(row.getCell(1).getNumericCellValue()));
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
                        MachineEntity machine = new MachineEntity();
                        machine.setSerialNumber(Double.toString(row.getCell(1).getNumericCellValue()));
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

            //парсинг задач рабочих
            sheet = workbook.getSheetAt(2);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                EmployeeEntity employee = employeeRepository.findByName(row.getCell(0).getStringCellValue()).orElseThrow(NotFoundEmployeeException::new);

                TimeSpendEntity timeSpend = new TimeSpendEntity();
                timeSpend.setStartTime(LocalDateTime.parse(row.getCell(1).getStringCellValue()));
                timeSpend.setEndTime(LocalDateTime.parse(row.getCell(2).getStringCellValue()));

                TaskEmployeeEntity taskEmployee = new TaskEmployeeEntity();
                taskEmployee.setExecutable(employee);
                taskEmployee.setTimeSpend(timeSpend);


                timeSpendRepository.save(timeSpend);
                employeeRepository.save(employee);
                taskEmployeeRepository.save(taskEmployee);
                if (sheet.getRow(i+1).getCell(0).getStringCellValue().isEmpty()){
                    break;
                }
            }

            //парсинг задач инвентаря
            sheet = workbook.getSheetAt(3);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                InventoryEntity inventory = inventoryRepository.findByName(row.getCell(0).getStringCellValue()).orElseThrow(NotFoundInventoryException::new);

                TimeSpendEntity timeSpend = new TimeSpendEntity();
                timeSpend.setStartTime(LocalDateTime.parse(row.getCell(1).getStringCellValue()));
                timeSpend.setEndTime(LocalDateTime.parse(row.getCell(2).getStringCellValue()));

                TaskInventoryEntity taskInventory = new TaskInventoryEntity();
                taskInventory.setExecutable(inventory);
                taskInventory.setTimeSpend(timeSpend);

                timeSpendRepository.save(timeSpend);
                inventoryRepository.save(inventory);
                taskInventoryRepository.save(taskInventory);
                if (sheet.getRow(i+1).getCell(0).getStringCellValue().isEmpty()){
                    break;
                }
            }
            workbook.close();
            Files.delete(excelFile.toPath());
        } catch (IOException e) {
            throw e;
        }
    }
}
