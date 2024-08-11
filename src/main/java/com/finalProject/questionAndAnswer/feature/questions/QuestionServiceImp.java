package com.finalProject.questionAndAnswer.feature.questions;

import com.finalProject.questionAndAnswer.domain.Question;
import com.finalProject.questionAndAnswer.domain.User;
import com.finalProject.questionAndAnswer.feature.questions.dto.QuestionRequest;
import com.finalProject.questionAndAnswer.feature.questions.dto.QuestionResponse;
import com.finalProject.questionAndAnswer.feature.questions.dto.QuestionUpdateRequest;
import com.finalProject.questionAndAnswer.feature.user.UserRepository;
import com.finalProject.questionAndAnswer.response_success.JavaResponse;
import com.finalProject.questionAndAnswer.response_success.JavaResponseCollection;
import com.finalProject.questionAndAnswer.response_success.ResponseSuccess;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QuestionServiceImp implements QuestionService {
    /**
     * Inject bean repository
     */
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    /**
     * variable message
     */
    private final String userNotFound = "User not found with uuid : ";
    private final String questionNotFound = "Question not found with uuid : ";

    /**
     * create question
     *
     * @param questionRequest contain data from client
     * @return object JavaResponse
     */
    @Override
    public JavaResponse<?> createQuestion(QuestionRequest questionRequest) {
        /**
         *  validate user exist or not
         */
        User user = userRepository.findByUuid(questionRequest.uuidUser())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        userNotFound + questionRequest.uuidUser()));

        /**
         * create object Question and set value
         */
        Question question = new Question();
        question.setUser(user);
        question.setTitle(questionRequest.title());
        question.setContent(questionRequest.content());
        question.setSnippedCode(questionRequest.snippedCode());
        question.setIsDeleted(true);
        question.setUuid(UUID.randomUUID().toString());
        questionRepository.save(question);

        /**
         * return type JavaResponse
         */
        return JavaResponse.builder()
                .data(mapToQuestionResponse(question))
                .build();
    }

    /**
     * @param pageNumber number of page (1,2,3,4...)
     * @param pageSize   number of items in per page (10,20,30...)
     * @param uuidUser   identity of user
     * @return object JavaResponseCollection
     */
    @Override
    public JavaResponseCollection<?> readQuestionsByUser(int pageNumber, int pageSize, String uuidUser) {
        /**
         * sort by id desc
         */
        Sort sortByIdDESC = Sort.by(Sort.Direction.DESC, "id");

        /**
         * create object PageRequest
         */
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize, sortByIdDESC);

        /**
         *  validate user exist or not
         */
        User user = userRepository.findByUuid(uuidUser)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, userNotFound + uuidUser));

        Page<Question> page = questionRepository.findByIsDeletedTrueAndUser(user, pageRequest);


        /**
         * stream page to List<QuestionResponse>
         */
        List<QuestionResponse> questionResponses = page.stream()
                .map(this::mapToQuestionResponse).toList();


        return JavaResponseCollection.builder()
                .count(page.getTotalElements())
                .data(questionResponses)
                .build();
    }

    /**
     * update question
     *
     * @param questionUpdateRequest contain data from client
     * @return JavaResponse
     */
    @Override
    public JavaResponse<?> updateQuestionByUser(QuestionUpdateRequest questionUpdateRequest, String uuidQuestion) {

        /**
         *  validate Question exist or not
         */
        Question question = questionRepository.findByUuidAndIsDeletedTrue(uuidQuestion)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, questionNotFound + uuidQuestion));

        /**
         * set new value
         */
        question.setTitle(questionUpdateRequest.title());
        question.setContent(questionUpdateRequest.content());
        question.setSnippedCode(questionUpdateRequest.snippedCode());
        questionRepository.save(question);


        return JavaResponse.builder()
                .data(mapToQuestionResponse(question))
                .build();
    }


    /**
     * read question by uuid
     *
     * @param uuidQuestion identity of question
     * @return JavaResponse
     */
    @Override
    public JavaResponse<?> readQuestionByUuid(String uuidQuestion) {
        /**
         *  validate Question exist or not
         */
        Question question = questionRepository.findByUuidAndIsDeletedTrue(uuidQuestion)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, questionNotFound + uuidQuestion));

        return JavaResponse.builder()
                .data(mapToQuestionResponse(question))
                .build();
    }

    /**
     *  delete question by uuid
     *  @param uuidQuestion identity of question
     */
    @Override
    public void deleteQuestionByUuid(String uuidQuestion) {
        /**
         *  validate Question exist or not
         */
        Question question = questionRepository.findByUuidAndIsDeletedTrue(uuidQuestion)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, questionNotFound + uuidQuestion));
        question.setIsDeleted(false);
        questionRepository.save(question);
    }

    /**
     * @param question object Question
     * @return object QuestionResponse
     */
    private QuestionResponse mapToQuestionResponse(Question question) {
        return QuestionResponse.builder()
                .title(question.getTitle())
                .content(question.getContent())
                .snippedCode(question.getSnippedCode())
                .uuidQuestion(question.getUuid())
                .build();
    }
}
