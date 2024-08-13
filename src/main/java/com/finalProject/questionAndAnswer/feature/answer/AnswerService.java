package com.finalProject.questionAndAnswer.feature.answer;

import com.finalProject.questionAndAnswer.feature.answer.dto.AnswerRequest;
import com.finalProject.questionAndAnswer.feature.answer.dto.AnswerResponse;
import com.finalProject.questionAndAnswer.response_success.JavaResponse;

public interface AnswerService {
    JavaResponse<?> postAnswer(AnswerRequest answerRequest);
}
