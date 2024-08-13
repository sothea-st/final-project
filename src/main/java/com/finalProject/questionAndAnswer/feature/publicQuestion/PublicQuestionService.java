package com.finalProject.questionAndAnswer.feature.publicQuestion;

import com.finalProject.questionAndAnswer.response_success.JavaResponseCollection;

public interface PublicQuestionService {
    /**
     * retrieve all questions
     * @param pageNumber number of page (1,2,3,4...)
     * @param pageSize number of items in per page (10,20,30...)
     * @return object JavaResponseCollection
     */
    JavaResponseCollection<?> publicQuestion(int pageNumber , int pageSize);
}
