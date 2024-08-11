package com.finalProject.questionAndAnswer.feature.fileUpload;

import com.finalProject.questionAndAnswer.feature.fileUpload.dto.FileUploadResponse;
import com.finalProject.questionAndAnswer.response_success.JavaResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileUploadService {
    JavaResponse<?> fileUpload(MultipartFile file);
    JavaResponse<?> fileUploadMultiple(List<MultipartFile> file);
    void deleteByFileName(String fileName);
}
