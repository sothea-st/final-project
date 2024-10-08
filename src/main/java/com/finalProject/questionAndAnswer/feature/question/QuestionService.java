package com.finalProject.questionAndAnswer.feature.question;

import com.finalProject.questionAndAnswer.feature.question.dto.QuestionRequest;
import com.finalProject.questionAndAnswer.feature.question.dto.QuestionUpdateRequest;
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

    /**
     * update question
     * @param questionUpdateRequest contain data from client
     * @return JavaResponse
     */
    JavaResponse<?> updateQuestionByUser(QuestionUpdateRequest questionUpdateRequest, String uuidQuestion);


    /**
     * read question by uuid
     * @param uuidQuestion identity of question
     * @return JavaResponse
     */
    JavaResponse<?> readQuestionByUuid(String uuidQuestion);


    /**
     *  delete question by uuid
     *  @param uuidQuestion identity of question
     */
    void deleteQuestionByUuid(String uuidQuestion);
}
