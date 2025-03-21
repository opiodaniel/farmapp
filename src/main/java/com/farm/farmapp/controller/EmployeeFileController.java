package com.farm.farmapp.controller;

import com.farm.farmapp.DTO.FileResponse;
import com.farm.farmapp.models.EmployeeFile;
import com.farm.farmapp.service.EmployeeFileService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/files")
public class EmployeeFileController {

    private final EmployeeFileService employeeFileService;

    public EmployeeFileController(EmployeeFileService employeeFileService) {
        this.employeeFileService = employeeFileService;
    }

    // 1. Upload a PDF file
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            EmployeeFile savedFile = employeeFileService.storeFile(file);
            return ResponseEntity.ok("File uploaded successfully with ID: " + savedFile.getId());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed.");
        }
    }
    // FileResponse comes from DTO package
    @GetMapping("/json_files")
    public ResponseEntity<List<FileResponse>> getFilesInJson() {
        List<FileResponse> files = employeeFileService.getFilesInJson();
        return ResponseEntity.ok(files);
    }

    @GetMapping
    public ResponseEntity<List<byte[]>> getAllFiles() {
        List<byte[]> fileContents = employeeFileService.getAllFiles();
        return ResponseEntity.ok()
                .body(fileContents); // Returns the raw file content
    }


    // 2. Download a file by ID
    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long id) {
        Optional<EmployeeFile> optionalFile = employeeFileService.getFile(id);
        if (optionalFile.isPresent()) {
            EmployeeFile employeeFile = optionalFile.get();
            MediaType fileType = MediaType.valueOf(employeeFile.getFileType());
            return ResponseEntity.ok()
                    .contentType(fileType)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + employeeFile.getFileName() + "\"")
                    .body(employeeFile.getFileData());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}

