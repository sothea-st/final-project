package com.finalProject.questionAndAnswer.feature.publicQuestion;

import com.finalProject.questionAndAnswer.domain.Question;
import com.finalProject.questionAndAnswer.feature.answer.dto.AnswerResponse;
import com.finalProject.questionAndAnswer.feature.image.dto.ImageResponse;
import com.finalProject.questionAndAnswer.feature.publicQuestion.dto.AuthorResponse;
import com.finalProject.questionAndAnswer.feature.publicQuestion.dto.CommentResponse;
import com.finalProject.questionAndAnswer.feature.publicQuestion.dto.DetailQuestionResponse;
import com.finalProject.questionAndAnswer.feature.publicQuestion.dto.PublicQuestionResponse;
import com.finalProject.questionAndAnswer.feature.question.QuestionRepository;
import com.finalProject.questionAndAnswer.feature.question.dto.QuestionResponse;
import com.finalProject.questionAndAnswer.response_success.JavaResponse;
import com.finalProject.questionAndAnswer.response_success.JavaResponseCollection;
import com.finalProject.questionAndAnswer.utils.JavaConstant;
import com.finalProject.questionAndAnswer.utils.ResponseLink;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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


    @Override
    public JavaResponse<?> readDetailPublicQuestion(String uuidQuestion) {
        Question question = questionRepository.findByUuidAndIsDeletedTrue(uuidQuestion)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Question not found with uuid " + uuidQuestion
                ));


        /**
         * map data to PublicQuestionResponse
         */
        DetailQuestionResponse detailQuestionResponses = DetailQuestionResponse.builder()
                .title(question.getTitle())
                .content(question.getContent())
                .snippedCode(question.getSnippedCode())
                .uuidQuestion(question.getUuid())
                .postDate(JavaConstant.dateFormat(String.valueOf(question.getCreatedAt())))
                .author(mapToAuthorResponse(question))
                .image(question.getImages() != null ? question.getImages().stream()
                        .map(img -> ImageResponse.builder()
                                .name(img.getImageName())
                                .uuidImage(img.getUuid())
                                .url(baseUrlImage + img.getImageName())
                                .link(null)
                                .build()).toList() : null
                )
                .comment(question.getComments().stream()
                        .map(comment -> CommentResponse.builder()
                                .comment(comment.getComment())
                                .userComment(question.getUser().getUserName())
                                .profileImage(question.getUser().getProfile())
                                .build()).toList())
                .answer(question.getAnswers().stream()
                        .map(answer -> AnswerResponse.builder()
                                .answer(answer.getAnswer())
                                .snippedCode(answer.getSnippedCode())
                                .userName(answer.getUser().getUserName())
                                .profileImage(answer.getUser().getProfile())
                                .build()).toList()
                )
                .build();


        return JavaResponse.builder()
                .data(detailQuestionResponses)
                .build();
    }

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
         * map data to PublicQuestionResponse
         */
        List<PublicQuestionResponse> publicQuestionResponses = pages.getContent().stream()
                .map(question -> PublicQuestionResponse.builder()
                        .title(question.getTitle())
                        .content(question.getContent())
                        .uuidQuestion(question.getUuid())
                        .postDate(JavaConstant.dateFormat(String.valueOf(question.getCreatedAt())))
                        .link(ResponseLink.methodGet(baseUrl + "public-questions/" + question.getUuid(),
                                "endpoint for get detail question"))
                        .build()).toList();

        return JavaResponseCollection.builder()
                .count(pages.getTotalElements())
                .data(publicQuestionResponses)
                .build();
    }

    private AuthorResponse mapToAuthorResponse(Question question) {
        return AuthorResponse.builder()
                .uuidUser(question.getUser().getUuid())
                .name(question.getUser().getUserName())
                .link(ResponseLink.methodGet(
                        baseUrlImage.replace("upload", "images") + question.getUser().getProfile(),
                        "endpoint for access profile photo author"))
                .build();
    }

}
