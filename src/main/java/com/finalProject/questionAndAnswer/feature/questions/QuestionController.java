package com.finalProject.questionAndAnswer.feature.questions;

import com.finalProject.questionAndAnswer.feature.questions.dto.QuestionRequest;
import com.finalProject.questionAndAnswer.feature.questions.dto.QuestionResponse;
import com.finalProject.questionAndAnswer.response_success.JavaResponse;
import com.finalProject.questionAndAnswer.response_success.JavaResponseCollection;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/questions")
@RequiredArgsConstructor
public class QuestionController {
    /**
     * Inject bean service
     */
    private final QuestionService questionService;


    @PostMapping
    JavaResponse<?> createQuestion(@Valid @RequestBody QuestionRequest questionRequest) {
        return questionService.createQuestion(questionRequest);
    }

    @GetMapping("/{uuidUser}")
    JavaResponseCollection<?> readQuestionsByUser(
            @PathVariable("uuidUser") String uuidUser,
            @RequestParam(name = "pageNumber" ,defaultValue = "1",required = false) int pageNumber,
            @RequestParam(name = "pageSize" , defaultValue = "10",required = false) int pageSize
    ) {
        return questionService.readQuestionsByUser(pageNumber, pageSize, uuidUser);
    }
}
