package com.farm.farmapp.service;

import com.farm.farmapp.DTO.FileResponse;
import com.farm.farmapp.models.EmployeeFile;
import com.farm.farmapp.repository.EmployeeFileRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeFileService {

    private final EmployeeFileRepository employeeFileRepository;

    public EmployeeFileService(EmployeeFileRepository employeeFileRepository) {
        this.employeeFileRepository = employeeFileRepository;
    }

    public EmployeeFile storeFile(MultipartFile file) throws IOException {
        EmployeeFile employeeFile = new EmployeeFile();
        employeeFile.setFileName(file.getOriginalFilename());
        employeeFile.setFileType(file.getContentType());
        employeeFile.setFileSize(file.getSize());
        employeeFile.setUploadDate(LocalDateTime.now());
        employeeFile.setFileData(file.getBytes());

        return employeeFileRepository.save(employeeFile);
    }


//    public List<FileResponse> getFilesInJson() {
//        List<EmployeeFile> files = employeeFileRepository.findAll();
//        return files.stream()
//                .map(file -> new FileResponse(file.getId(), file.getFileName(), file.getFileType(), file.getFileSize(), file.getUploadDate()))
//                .collect(Collectors.toList());
//    }
//
//    public List<byte[]> getAllFiles() {
//        List<EmployeeFile> files = employeeFileRepository.findAll();
//
//        return files.stream()
//                .map(EmployeeFile::getFileData) // Extracts the file content
//                .collect(Collectors.toList());
//    }


    public Optional<EmployeeFile> getFile(Long id) {
        return employeeFileRepository.findById(id);
    }
}

