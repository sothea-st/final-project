package com.finalProject.questionAndAnswer.feature.publicQuestion;

import com.finalProject.questionAndAnswer.response_success.JavaResponseCollection;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/public-questions")
@RequiredArgsConstructor
public class PublicQuestionController {
    private final PublicQuestionService publicQuestionService;

    /**
     * retrieve all questions
     * @param pageNumber number of page (1,2,3,4...)
     * @param pageSize number of items in per page (10,20,30...)
     * @return object JavaResponseCollection
     */
    @GetMapping
    public JavaResponseCollection<?>  publicQuestion(
            @RequestParam(name = "pageNumber" ,defaultValue = "1" , required = false) int pageNumber ,
            @RequestParam(name = "pageSize" ,defaultValue = "10" , required = false) int pageSize
    ) {
        return publicQuestionService.publicQuestion(pageNumber,pageSize);
    }


}
