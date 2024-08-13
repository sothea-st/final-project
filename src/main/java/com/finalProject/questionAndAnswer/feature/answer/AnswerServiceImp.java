package com.finalProject.questionAndAnswer.feature.answer;

import com.finalProject.questionAndAnswer.domain.Answer;
import com.finalProject.questionAndAnswer.domain.Question;
import com.finalProject.questionAndAnswer.domain.User;
import com.finalProject.questionAndAnswer.feature.answer.dto.AnswerRequest;
import com.finalProject.questionAndAnswer.feature.answer.dto.AnswerResponse;
import com.finalProject.questionAndAnswer.feature.question.QuestionRepository;
import com.finalProject.questionAndAnswer.feature.user.UserRepository;
import com.finalProject.questionAndAnswer.response_success.JavaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AnswerServiceImp implements AnswerService {
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    @Override
    public JavaResponse<?> postAnswer(AnswerRequest answerRequest) {
        /**
         * validate user exist or not
         */
        User user = userRepository.findByUuidAndIsDeletedTrue(answerRequest.uuidUser())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User Not Found with uuid " + answerRequest.uuidUser()));
        /**
         * validate question exist or not
         */
        Question question = questionRepository.findByUuidAndIsDeletedTrue(answerRequest.uuidQuestion())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Question Not Found with uuid " + answerRequest.uuidQuestion()));

        Answer answer = new Answer();
        answer.setAnswer(answerRequest.answer());
        answer.setSnippedCode(answerRequest.snippedCode());
        answer.setQuestion(question);
        answer.setUser(user);
        answer.setIsDeleted(true);
        answer.setUuid(UUID.randomUUID().toString());
        answerRepository.save(answer);

//        String answer,
//        String snippedCode,
//        String userName,
//        String profileImage

        return JavaResponse.builder()
                .data(AnswerResponse.builder()
                        .answer(answer.getAnswer())
                        .snippedCode(answer.getSnippedCode())
                        .userName(answer.getUser().getUserName())
                        .profileImage(answer.getUser().getProfile())
                        .build())
                .build();
    }
}












