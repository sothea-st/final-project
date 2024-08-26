package com.finalProject.questionAndAnswer.feature.publicQuestion;

import com.finalProject.questionAndAnswer.domain.Answer;
import com.finalProject.questionAndAnswer.domain.Comment;
import com.finalProject.questionAndAnswer.domain.Question;
import com.finalProject.questionAndAnswer.feature.answer.dto.AnswerResponse;
import com.finalProject.questionAndAnswer.feature.image.dto.ImageResponse;
import com.finalProject.questionAndAnswer.feature.publicQuestion.dto.AuthorResponse;

import com.finalProject.questionAndAnswer.feature.publicQuestion.dto.CommentResponse;
import com.finalProject.questionAndAnswer.feature.publicQuestion.dto.DetailQuestionResponse;
import com.finalProject.questionAndAnswer.feature.publicQuestion.dto.PublicQuestionResponse;
import com.finalProject.questionAndAnswer.feature.question.QuestionRepository;
import com.finalProject.questionAndAnswer.feature.vote.VoteRepository;
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

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicQuestionServiceImp implements PublicQuestionService {
    /**
     * inject bean repository
     */
    private final QuestionRepository questionRepository;
    private final VoteRepository voteRepository;

    /**
     * get value from application.properties base-url
     */
    @Value("${base-url}")
    private String baseUrl;

    @Value("${base-url-public}")
    private String baseUrlPublic;

    @Value("${file-upload.base-uri}")
    private String baseUrlImage;

    @Value("${base-url.read-image}")
    private String baseUrlImagePublic;


    @Override
    public JavaResponse<?> readDetailPublicQuestion(String uuidQuestion) {
        Question question = questionRepository.findByUuidAndIsDeletedTrue(uuidQuestion)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Question not found with uuid " + uuidQuestion
                ));


        int countSignPlus = voteRepository.countByIsDeletedTrueAndQuestion(question);
        int countSignMinus = voteRepository.countByIsDeletedFalseAndQuestion(question);
        int totalSign = countSignPlus - countSignMinus;

        /**
         * map data to PublicQuestionResponse
         */
        DetailQuestionResponse detailQuestionResponses = DetailQuestionResponse.builder()
                .title(question.getTitle())
                .content(question.getContent())
                .snippedCode(question.getSnippedCode())
                .uuidQuestion(question.getUuid())
                .postDate(JavaConstant.dateFormat(String.valueOf(question.getCreatedAt())))
                .vote(totalSign)
                .author(mapToAuthorResponse(question))
                .image(mapToImageResponse(question))
                .comment(mapToCommentResponse(question))
                .answer(question.getAnswers().stream()
                        .filter(Answer::getIsDeleted)
                        .sorted(Comparator.comparing(Answer::getCreatedAt))
                        .map(answer -> {
                                    int countSignPlusAnswer = voteRepository.countByIsDeletedTrueAndAnswer(answer);
                                    int countSignMinusAnswer = voteRepository.countByIsDeletedFalseAndAnswer(answer);
                                    int totalSignAnswer = countSignPlusAnswer - countSignMinusAnswer;
                                    return AnswerResponse.builder()
                                            .answer(answer.getAnswer())
                                            .snippedCode(answer.getSnippedCode())
                                            .postDate(JavaConstant.dateFormat(String.valueOf(answer.getCreatedAt())))
                                            .vote(totalSignAnswer)
                                            .uuidAnswer(answer.getUuid())
                                            .author(AuthorResponse.builder()
                                                    .name(answer.getUser().getUserName())
                                                    .uuidUser(answer.getUser().getUuid())
                                                    .profileImage(baseUrlImagePublic+ answer.getUser().getProfile())
                                                    .build())
                                            .link(List.of(
                                                    ResponseLink.methodPut(baseUrl + "answers/" + answer.getUuid(), "endpoint for update"),
                                                    ResponseLink.methodDelete(baseUrl + "answers/" + answer.getUuid(), "endpoint for delete")
                                            ))
                                            .image(answer.getImages() != null ? answer.getImages().stream()
                                                    .map(img -> ImageResponse.builder()
                                                            .name(img.getImageName())
                                                            .uuidImage(img.getUuid())
                                                            .url(baseUrlImagePublic + img.getImageName())
                                                            .link(ResponseLink.methodDelete(baseUrl + "images/" + img.getImageName(), "endpoint for delete image"))
                                                            .build()).toList() : null
                                            )
                                            .comments(mapToCommentResponse(answer, question))
                                            .build();
                                }
                        ).toList()
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
                        .link(ResponseLink.methodGet(baseUrlPublic + "public-questions/" + question.getUuid(),
                                "endpoint for access detail question"))
                        .build()).toList();

        return JavaResponseCollection.builder()
                .count(pages.getTotalElements())
                .data(publicQuestionResponses)
                .build();
    }

    private AuthorResponse mapToAuthorResponse(Question question) {
        return AuthorResponse.builder()
                .name(question.getUser().getUserName())
                .uuidUser(question.getUser().getUuid())
                .profileImage(baseUrlImagePublic + question.getUser().getProfile())
                .build();
    }

    private List<ImageResponse> mapToImageResponse(Question question) {
        return question.getImages() != null ? question.getImages().stream()
                .map(img -> ImageResponse.builder()
                        .name(img.getImageName())
                        .uuidImage(img.getUuid())
                        .url(baseUrlImagePublic + img.getImageName())
                        .link(null)
                        .build()).toList() : null;
    }

    private List<CommentResponse> mapToCommentResponse(Question question) {
        return question.getComments().stream()
                .filter(Comment::getIsDeleted)
                .sorted(Comparator.comparing(Comment::getCreatedAt))
                .map(comment -> CommentResponse.builder()
                        .comment(comment.getComment())
                        .userComment(comment.getUser().getUserName())
                        .uuidUser(comment.getUser().getUuid())
                        .uuidComment(comment.getUuid())
                        .profileImage(baseUrlImagePublic + question.getUser().getProfile())
                        .postDate(JavaConstant.dateFormat(String.valueOf(comment.getCreatedAt())))
                        .build()).toList();
    }

    private List<CommentResponse> mapToCommentResponse(Answer answer, Question question) {
        return answer.getComments().stream()
                .filter(Comment::getIsDeleted)
                .sorted(Comparator.comparing(Comment::getCreatedAt))
                .map(c -> CommentResponse.builder()
                        .comment(c.getComment())
                        .userComment(c.getUser().getUserName())
                        .uuidUser(c.getUser().getUuid())
                        .uuidComment(c.getUuid())
                        .profileImage(baseUrlImagePublic + question.getUser().getProfile())
                        .postDate(JavaConstant.dateFormat(String.valueOf(c.getCreatedAt())))
                        .build()).toList();
    }

}
