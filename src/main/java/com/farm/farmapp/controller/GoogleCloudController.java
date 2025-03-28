package com.farm.farmapp.controller;

import com.farm.farmapp.models.EmployeeFile;
import com.farm.farmapp.models.Employees;
import com.farm.farmapp.repository.EmployeeFileRepository;
import com.farm.farmapp.service.GoogleCloudStorageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/files")
public class GoogleCloudController  {

    private final GoogleCloudStorageService storageService;
    private final EmployeeFileRepository employeeFileRepository;

    public GoogleCloudController (GoogleCloudStorageService storageService, EmployeeFileRepository employeeFileRepository) {
        this.storageService = storageService;
        this.employeeFileRepository = employeeFileRepository;
    }

    @PostMapping("/upload")
    public ResponseEntity<EmployeeFile> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        String fileUrl = storageService.uploadFile(file);

        //storageService.printBucketName();
        //storageService.printGcpFileName();

        EmployeeFile employeeFile = new EmployeeFile();
        employeeFile.setFileName(file.getOriginalFilename());
        employeeFile.setFileType(file.getContentType());
        employeeFile.setFileSize(file.getSize());
        employeeFile.setUploadDate(LocalDateTime.now());
        employeeFile.setFileUrl(fileUrl);  // Store URL, not actual file
        //employeeFile.setEmployee(new Employees(employeeId)); // Assuming Employee object is referenced by ID

        EmployeeFile savedFile = employeeFileRepository.save(employeeFile);
        return ResponseEntity.ok(savedFile);
    }

}

