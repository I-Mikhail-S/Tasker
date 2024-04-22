package ru.samgtu.tasker.service;

import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.samgtu.tasker.entity.EmployeeEntity;
import ru.samgtu.tasker.entity.TaskEmployeeEntity;
import ru.samgtu.tasker.entity.TimeSpendEntity;
import ru.samgtu.tasker.repository.EmployeeRepository;
import ru.samgtu.tasker.repository.TaskEmployeeRepository;
import ru.samgtu.tasker.repository.TaskInventoryRepository;
import ru.samgtu.tasker.repository.TimeSpendRepository;
import ru.samgtu.tasker.util.FileUtil;
import org.apache.poi.xssf.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;

@Service
public class DataLoaderService {
    private final FileUtil fileUtil;
    private final EmployeeRepository employeeRepository;
    private final TaskEmployeeRepository taskEmployeeRepository;
    private final TaskInventoryRepository taskInventoryRepository;
    private final TimeSpendRepository timeSpendRepository;
    @Autowired
    public DataLoaderService(FileUtil fileUtil, EmployeeRepository employeeRepository, TaskEmployeeRepository taskEmployeeRepository, TaskInventoryRepository taskInventoryRepository, TimeSpendRepository timeSpendRepository) {
        this.fileUtil = fileUtil;
        this.employeeRepository = employeeRepository;
        this.taskEmployeeRepository = taskEmployeeRepository;
        this.taskInventoryRepository = taskInventoryRepository;
        this.timeSpendRepository = timeSpendRepository;
    }

    public void parseExcel(MultipartFile file) throws IOException {
        File excelFile = null;
        try {
            excelFile = fileUtil.convertMultiPartFileToFile(file);

            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(excelFile));

            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                EmployeeEntity employee = new EmployeeEntity();
                employee.setName(row.getCell(0).getStringCellValue());

                TimeSpendEntity timeSpend = new TimeSpendEntity();
                timeSpend.setStartTime(LocalDateTime.parse(row.getCell(1).getStringCellValue()));
                timeSpend.setEndTime(LocalDateTime.parse(row.getCell(2).getStringCellValue()));

                TaskEmployeeEntity taskEmployee = new TaskEmployeeEntity();
                taskEmployee.setExecutable(employee);
                taskEmployee.setTimeSpend(timeSpend);

                timeSpendRepository.save(timeSpend);
                employeeRepository.save(employee);
                taskEmployeeRepository.save(taskEmployee);
            }

//            sheet = workbook.getSheetAt(1);
//
//            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
//                Row row = sheet.getRow(i);
//                EmployeeEntity employee = new EmployeeEntity();
//                employee.setName(row.getCell(0).getStringCellValue());
//
//                TimeSpendEntity timeSpend = new TimeSpendEntity();
//                timeSpend.setStartTime(LocalDateTime.parse(row.getCell(1).getStringCellValue()));
//                timeSpend.setEndTime(LocalDateTime.parse(row.getCell(2).getStringCellValue()));
//
//                TaskEmployeeEntity taskEmployee = new TaskEmployeeEntity();
//                taskEmployee.setExecutable(employee);
//                taskEmployee.setTimeSpend(timeSpend);
//
//                timeSpendRepository.save(timeSpend);
//                employeeRepository.save(employee);
//                taskEmployeeRepository.save(taskEmployee);
//            }
            workbook.close();
            Files.delete(excelFile.toPath());
        } catch (IOException e) {
            throw e;
        }
    }
}
