package com.farm.farmapp.DTO;

import java.time.LocalDateTime;

public class FileResponse {
    private Long id;
    private String fileName;
    private String fileType;
    private Long fileSize;
    private LocalDateTime uploadDate;

    public FileResponse(Long id, String fileName, String fileType, long size, LocalDateTime uploadDate) {
        this.id = id;
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileSize = size;
        this.uploadDate = uploadDate;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public LocalDateTime getUploadDate() {
        return uploadDate;
    }

    // Setters (optional)
    public void setId(Long id) {
        this.id = id;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public void setUploadDate(LocalDateTime uploadDate) {
        this.uploadDate = uploadDate;
    }
}
