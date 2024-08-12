package com.finalProject.questionAndAnswer.feature.image;

import com.finalProject.questionAndAnswer.domain.Image;
import com.finalProject.questionAndAnswer.response_success.ResponseSuccess;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ImageServiceImp implements ImageService{
    private final ImageRepository imageRepository;

    /**
     * delete image by imageName
     * @param imageName image name
     * @return object  ResponseSuccess
     */
    @Override
    public ResponseSuccess delete(String imageName) {
        Image image = imageRepository.findByImageName(imageName)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND ,"Image not found with imageName : " + imageName));
        imageRepository.delete(image);
        return ResponseSuccess.builder().build();
    }
}
