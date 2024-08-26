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
    /**
     * server-path get value from application.properties
     */
    @Value("${server-path}")
    private String serverPath;

    /**
     * file-upload.base-uri get value from application.properties
     */
    @Value("${file-upload.base-uri}")
    private String baseUri;

    @Value("${base-url.read-image}")
    private String baseUrlImagePublic;

    /**
     * file upload
     * @param file is object MultipartFile
     * @return object JavaResponse
     */
    @Override
    public JavaResponse<?> fileUpload(MultipartFile file) {
        return JavaResponse.builder()
                .data(fileUploadResponse(file))
                .build();
    }

    /**
     * fileUploadResponse
     * @param file from MultipartFile
     * @return object FileUploadResponse
     */
    private FileUploadResponse fileUploadResponse(MultipartFile file) {
        String fileName = UUID.randomUUID().toString();
        String extension = file.getOriginalFilename().split("\\.")[1];
        fileName = fileName + "." + extension;
        Path path = Paths.get(serverPath + "/" + fileName);
        try {
            Files.copy(file.getInputStream(), path);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INSUFFICIENT_STORAGE, "Upload file failed : " +file.getSize());
        }
        return FileUploadResponse.builder()
                .name(fileName)
                .contentType(file.getContentType())
                .size(file.getSize())
                .uri(baseUrlImagePublic + fileName)
                .build();
    }

    /**
     * file uploadMultiple
     * @param files is object MultipartFile
     * @return object JavaResponse
     */
    @Override
    public JavaResponse<?> fileUploadMultiple(List<MultipartFile> files) {
        List<FileUploadResponse> fileUploadResponses = new ArrayList<>();
        files.forEach(file -> fileUploadResponses.add(fileUploadResponse(file)));
        return JavaResponse.builder()
                .data(fileUploadResponses)
                .build();

    }

    /**
     * delete file
     * @param fileName name of file
     */
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
