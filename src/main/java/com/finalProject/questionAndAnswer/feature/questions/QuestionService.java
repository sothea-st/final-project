package com.finalProject.questionAndAnswer.feature.questions;

import com.finalProject.questionAndAnswer.feature.questions.dto.QuestionRequest;
import com.finalProject.questionAndAnswer.response_success.JavaResponse;
import com.finalProject.questionAndAnswer.response_success.JavaResponseCollection;

public interface QuestionService {
    /**
     * create question
     * @param questionRequest contain data from client
     * @return JavaResponse
     */
    JavaResponse<?> createQuestion(QuestionRequest questionRequest);

    /**
     * @param pageNumber number of page (1,2,3,4...)
     * @param pageSize number of items in per page (10,20,30...)
     * @param uuidUser identity of user
     * @return object JavaResponseCollection
     */
    JavaResponseCollection<?> readQuestionsByUser(int pageNumber , int pageSize, String uuidUser);


}
