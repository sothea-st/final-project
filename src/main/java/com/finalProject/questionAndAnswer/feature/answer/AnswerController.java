package com.finalProject.questionAndAnswer.feature.answer;

import com.finalProject.questionAndAnswer.feature.answer.dto.AnswerRequest;
import com.finalProject.questionAndAnswer.response_success.JavaResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/answers")
public class AnswerController {
    private final AnswerService answerService;

    @PostMapping
    public JavaResponse<?> postAnswer(@Valid @RequestBody AnswerRequest answerRequest) {
        return answerService.postAnswer(answerRequest);
    }

}
