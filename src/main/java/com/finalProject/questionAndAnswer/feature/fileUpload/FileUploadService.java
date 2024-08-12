package com.finalProject.questionAndAnswer.feature.fileUpload;

import com.finalProject.questionAndAnswer.feature.fileUpload.dto.FileUploadResponse;
import com.finalProject.questionAndAnswer.response_success.JavaResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileUploadService {
    /**
     * file upload
     * @param file is object MultipartFile
     * @return object JavaResponse
     */
    JavaResponse<?> fileUpload(MultipartFile file);

    /**
     * file uploadMultiple
     * @param file is object MultipartFile
     * @return object JavaResponse
     */
    JavaResponse<?> fileUploadMultiple(List<MultipartFile> file);
    /**
     * delete file
     * @param fileName name of file
     */
    void deleteByFileName(String fileName);
}
