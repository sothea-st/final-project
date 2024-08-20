package com.finalProject.questionAndAnswer.feature.vote;

import com.finalProject.questionAndAnswer.domain.Answer;
import com.finalProject.questionAndAnswer.domain.Question;
import com.finalProject.questionAndAnswer.domain.User;
import com.finalProject.questionAndAnswer.domain.Vote;
import com.finalProject.questionAndAnswer.feature.answer.AnswerRepository;
import com.finalProject.questionAndAnswer.feature.question.QuestionRepository;
import com.finalProject.questionAndAnswer.feature.user.UserRepository;
import com.finalProject.questionAndAnswer.feature.vote.dto.VoteRequestQuestion;
import com.finalProject.questionAndAnswer.feature.vote.dto.VoteResponse;
import com.finalProject.questionAndAnswer.response_success.JavaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VoteServiceImp implements VoteService {
    // inject bean repository
    private final VoteRepository voteRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;


    @Override
    public JavaResponse<?> addVoteQuestion(VoteRequestQuestion voteRequestQuestion) {

        User user = userRepository.findByUuidAndIsDeletedTrue(voteRequestQuestion.uuidUser())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "User not found with uuid " + voteRequestQuestion.uuidUser()));


        Question question = questionRepository.findByUuidAndIsDeletedTrue(voteRequestQuestion.uuidQuestion())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Question not found with uuid " + voteRequestQuestion.uuidQuestion()));



        Vote vote = new Vote();
        vote.setQuestion(question);
        vote.setUser(user);
        vote.setIsDeleted(true);
        vote.setUuid(UUID.randomUUID().toString());
        voteRepository.save(vote);


        int countSignPlus = voteRepository.countByIsDeletedTrue();
        int countSignMinus = voteRepository.countByIsDeletedFalse();
        int totalSign = countSignPlus - countSignMinus;

        return JavaResponse.builder()
                .data(VoteResponse.builder()
                        .vote(totalSign)
                        .build())
                .build();

    }
}
