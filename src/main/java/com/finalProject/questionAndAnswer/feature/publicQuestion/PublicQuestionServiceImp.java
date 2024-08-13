package com.finalProject.questionAndAnswer.feature.publicQuestion;

import com.finalProject.questionAndAnswer.domain.Question;
import com.finalProject.questionAndAnswer.feature.image.dto.ImageResponse;
import com.finalProject.questionAndAnswer.feature.publicQuestion.dto.AuthorResponse;
import com.finalProject.questionAndAnswer.feature.publicQuestion.dto.PublicQuestionResponse;
import com.finalProject.questionAndAnswer.feature.question.QuestionRepository;
import com.finalProject.questionAndAnswer.feature.question.dto.QuestionResponse;
import com.finalProject.questionAndAnswer.response_success.JavaResponseCollection;
import com.finalProject.questionAndAnswer.utils.JavaConstant;
import com.finalProject.questionAndAnswer.utils.ResponseLink;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicQuestionServiceImp implements PublicQuestionService {
    /**
     * inject bean repository
     */
    private final QuestionRepository questionRepository;

    /**
     * get value from application.properties base-url
     */
    @Value("${base-url}")
    private String baseUrl;

    @Value("${file-upload.base-uri}")
    private String baseUrlImage;


    /**
     * retrieve all questions
     *
     * @param pageNumber number of page (1,2,3,4...)
     * @param pageSize   number of items in per page (10,20,30...)
     * @return object JavaResponseCollection
     */
    @Override
    public JavaResponseCollection<?> publicQuestion(int pageNumber, int pageSize) {
        /**
         * sort by created_at desc
         */
        Sort sortByCreatedAt = Sort.by(Sort.Direction.DESC, "createdAt");
        /**
         * create object PageRequest
         */
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize, sortByCreatedAt);

        Page<Question> pages = questionRepository.findByIsDeletedTrue(pageRequest);

        /**
         * map data to questionResponse
         */
        List<PublicQuestionResponse> publicQuestionResponses = pages.getContent().stream()
                .map(question -> PublicQuestionResponse.builder()
                        .author(AuthorResponse.builder()
                                .uuidUser(question.getUser().getUuid())
                                .name(question.getUser().getUserName())
                                .link(ResponseLink.methodGet(baseUrlImage + question.getUser().getProfile(), "endpoint for access profile photo author"))
                                .build())
                        .title(question.getTitle())
                        .content(question.getContent())
                        .snippedCode(question.getSnippedCode())
                        .uuidQuestion(question.getUuid())
                        .link(ResponseLink.methodGet(baseUrl + "questions/" + question.getUuid(), "endpoint for access detail question"))
                        .image(question.getImages() != null ? question.getImages().stream()
                                .map(img -> ImageResponse.builder()
                                        .name(img.getImageName())
                                        .uuidImage(img.getUuid())
                                        .url(baseUrlImage + img.getImageName())
                                        .link(ResponseLink.methodDelete(baseUrl + "images/" + img.getImageName(), "endpoint for delete image"))
                                        .build()).toList() : null
                        )
                        .postDate("")
                        .build()).toList();


        return JavaResponseCollection.builder()
                .count(pages.getTotalElements())
                .data(publicQuestionResponses)
                .build();
    }

}
