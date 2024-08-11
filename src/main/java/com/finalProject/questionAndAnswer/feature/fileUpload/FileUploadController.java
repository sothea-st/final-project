package com.finalProject.questionAndAnswer.feature.fileUpload;

import com.finalProject.questionAndAnswer.feature.fileUpload.dto.FileUploadResponse;
import com.finalProject.questionAndAnswer.response_success.JavaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/upload")
@RequiredArgsConstructor
public class FileUploadController {
    private final FileUploadService fileUploadService;
    @PostMapping
    JavaResponse<?> upload(@RequestPart MultipartFile file){
        return fileUploadService.fileUpload(file);
    }

    @PostMapping("/multiple")
    JavaResponse<?> uploadMultiple(@RequestPart List<MultipartFile> files){
        return fileUploadService.fileUploadMultiple(files);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{fileName}")
    void deleteByFileName(@PathVariable String fileName) {
        fileUploadService.deleteByFileName(fileName);
    }
}
