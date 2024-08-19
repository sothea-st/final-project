package com.finalProject.questionAndAnswer.feature.answer;

import com.finalProject.questionAndAnswer.feature.answer.dto.AnswerRequest;
import com.finalProject.questionAndAnswer.response_success.JavaResponse;
import com.finalProject.questionAndAnswer.response_success.ResponseSuccess;
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

    @PutMapping("/{uuidAnswer}")
    public JavaResponse<?> updateAnswer(@Valid @RequestBody AnswerRequest answerRequest , @PathVariable String uuidAnswer) {
        return answerService.updateAnswer(answerRequest, uuidAnswer);
    }

    @DeleteMapping("/{uuidAnswer}")
    public ResponseSuccess deleteAnswer(@PathVariable String uuidAnswer) {
        return answerService.deleteAnswer(uuidAnswer);
    }

}
