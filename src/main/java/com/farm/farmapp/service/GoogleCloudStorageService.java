package com.farm.farmapp.service;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class GoogleCloudStorageService {

    @Value("${google.cloud.bucket}")
    private String BUCKET_NAME;

    private Storage storage;

    public GoogleCloudStorageService() {
        try {
            InputStream credentialsStream = getClass().getClassLoader().getResourceAsStream("niye-386508-e0fccb8a7dbf.json");
            if (credentialsStream == null) {
                throw new IOException("GCP credentials file not found");
            }
            this.storage = StorageOptions.newBuilder()
                    .setCredentials(GoogleCredentials.fromStream(credentialsStream))
                    .build()
                    .getService();
        } catch (IOException e) {
            System.err.println("Error initializing Google Cloud Storage: " + e.getMessage());
            this.storage = null; // Storage is null if initialization fails
        }
    }

    public String uploadFile(MultipartFile file) throws IOException {
        if (storage == null) {
            throw new IOException("Google Cloud Storage is not initialized. Check your credentials.");
        }

        // Generate a unique file name
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        BlobId blobId = BlobId.of(BUCKET_NAME, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType(file.getContentType())
                .build();

        // Upload file
        storage.create(blobInfo, file.getBytes());

        // Return file URL
        return String.format("https://storage.googleapis.com/%s/%s", BUCKET_NAME, fileName);
    }

    public void deleteFile(String fileUrl) throws IOException {
        if (storage == null) {
            throw new IOException("Google Cloud Storage is not initialized. Check your credentials.");
        }

        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        BlobId blobId = BlobId.of(BUCKET_NAME, fileName);
        storage.delete(blobId);
    }
}

