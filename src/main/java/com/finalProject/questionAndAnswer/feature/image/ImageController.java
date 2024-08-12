package com.finalProject.questionAndAnswer.feature.image;

import com.finalProject.questionAndAnswer.response_success.ResponseSuccess;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/images")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    /**
     * delete image by imageName
     * @param imageName image name
     * @return object  ResponseSuccess
     */
    @DeleteMapping("/{imageName}")
    public ResponseSuccess deleteImage(@PathVariable("imageName") String imageName) {
        imageService.delete(imageName);
        return ResponseSuccess.builder().build();
    }
}
