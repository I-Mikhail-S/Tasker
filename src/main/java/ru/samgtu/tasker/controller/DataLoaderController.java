package ru.samgtu.tasker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.samgtu.tasker.service.DataLoaderService;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/dataloader")
public class DataLoaderController {
    private final DataLoaderService dataLoaderService;
    @Autowired
    public DataLoaderController(DataLoaderService dataLoaderService) {
        this.dataLoaderService = dataLoaderService;
    }

    @PostMapping("/excel")
    public ResponseEntity<?> loadDataFromExcel(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        dataLoaderService.parseExcel(multipartFile);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
