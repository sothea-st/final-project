package com.finalProject.questionAndAnswer.feature.image;

import com.finalProject.questionAndAnswer.response_success.ResponseSuccess;

public interface ImageService {
    /**
     * delete image
     * @param imageName file name image
     * @return object ResponseSuccess
     */
    ResponseSuccess delete(String imageName);
}
