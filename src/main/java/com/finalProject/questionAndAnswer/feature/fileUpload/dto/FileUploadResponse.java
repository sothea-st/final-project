package com.finalProject.questionAndAnswer.feature.fileUpload.dto;

import lombok.Builder;

@Builder
public record FileUploadResponse(
        String name,
        String uri,
        String contentType,
        Long size
) {
}
