package com.finalProject.questionAndAnswer.feature.fileUpload;

import com.finalProject.questionAndAnswer.feature.fileUpload.dto.FileUploadResponse;
import com.finalProject.questionAndAnswer.response_success.JavaResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FileUploadServiceImp implements FileUploadService {

    @Value("${server-path}")
    private String serverPath;

    @Value("${file-upload.base-uri}")
    private String baseUri;


    @Override
    public JavaResponse<?> fileUpload(MultipartFile file) {
        return JavaResponse.builder()
                .data(fileUploadResponse(file))
                .build();
    }

    private FileUploadResponse fileUploadResponse(MultipartFile file) {
        String fileName = UUID.randomUUID().toString();
        String extension = file.getOriginalFilename().split("\\.")[1];
        fileName = fileName + "." + extension;
        Path path = Paths.get(serverPath + "/" + fileName);
        try {
            Files.copy(file.getInputStream(), path);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INSUFFICIENT_STORAGE, "Upload file failed");
        }
        return FileUploadResponse.builder()
                .name(fileName)
                .contentType(file.getContentType())
                .size(file.getSize())
                .uri(baseUri + fileName)
                .build();
    }

    @Override
    public JavaResponse<?> fileUploadMultiple(List<MultipartFile> files) {
        List<FileUploadResponse> fileUploadResponses = new ArrayList<>();
        files.forEach(file -> fileUploadResponses.add(fileUploadResponse(file)));
        return JavaResponse.builder()
                .data(fileUploadResponses)
                .build();

    }

    @Override
    public void deleteByFileName(String fileName) {
        Path path = Paths.get(serverPath + fileName);
        if (Files.exists(path)) {
            try {
                Files.delete(path);
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                        "File is failed to delete");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "File name is not found");
        }
    }
}
