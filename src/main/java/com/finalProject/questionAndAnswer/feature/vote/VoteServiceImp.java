package com.finalProject.questionAndAnswer.feature.vote;

import com.finalProject.questionAndAnswer.domain.Answer;
import com.finalProject.questionAndAnswer.domain.Question;
import com.finalProject.questionAndAnswer.domain.User;
import com.finalProject.questionAndAnswer.domain.Vote;
import com.finalProject.questionAndAnswer.feature.answer.AnswerRepository;
import com.finalProject.questionAndAnswer.feature.question.QuestionRepository;
import com.finalProject.questionAndAnswer.feature.user.UserRepository;
import com.finalProject.questionAndAnswer.feature.vote.dto.VoteRequestAnswer;
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
    public JavaResponse<?> unVoteAnswer(VoteRequestAnswer voteRequestAnswer) {
        return voteAndUnVoteAnswer(voteRequestAnswer, 1);
    }

    @Override
    public JavaResponse<?> addVoteAnswer(VoteRequestAnswer voteRequestAnswer) {
        return voteAndUnVoteAnswer(voteRequestAnswer, 0);
    }

    @Override
    public JavaResponse<?> unVoteQuestion(VoteRequestQuestion voteRequestQuestion) {
        return voteAndUnVoteQuestion(voteRequestQuestion, 1);
    }

    @Override
    public JavaResponse<?> addVoteQuestion(VoteRequestQuestion voteRequestQuestion) {
        return voteAndUnVoteQuestion(voteRequestQuestion, 0);
    }


    private JavaResponse<?> voteAndUnVoteAnswer(VoteRequestAnswer voteRequestAnswer, int code) {
        User user = userRepository.findByUuidAndIsDeletedTrue(voteRequestAnswer.uuidUser())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "User not found with uuid " + voteRequestAnswer.uuidUser()));

        Answer answer = answerRepository.findByUuidAndIsDeletedTrue(voteRequestAnswer.uuidAnswer())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Answer not found with uuid " + voteRequestAnswer.uuidAnswer()));

        // User must login first
        if (user.getIsVerify() == null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User must login first to vote or unvote to answer ");
        }

        if (code == 0) { // code = 0 => addVote
            if (voteRepository.existsByUserAndAnswerAndIsDeletedTrue(user, answer)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "User already voted to answer ");
            }
            // Check if the user has never voted on this question
            if (!voteRepository.existsByUserAndAnswer(user, answer)) {
                Vote vote = new Vote();
                vote.setAnswer(answer);
                vote.setUser(user);
                vote.setIsDeleted(true);
                vote.setUuid(UUID.randomUUID().toString());
                voteRepository.save(vote);
            } else {
                // Check if user has voted on question
                Vote vote = voteRepository.findByUserAndAnswer(user, answer)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with uuidUser : "
                                + voteRequestAnswer.uuidUser() + " and Answer not found with uuidAnswer : " + voteRequestAnswer.uuidAnswer()));
                vote.setIsDeleted(true); // update isDeleted to true means  vote
                voteRepository.save(vote);
            }
        } else { // code = 1 => unVote

            // Check if the user has previously removed their vote for the given question
            if (voteRepository.existsByUserAndAnswerAndIsDeletedFalse(user, answer)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "User already remove vote to answer ");
            }

            // Check if the user has never voted on this question
            if (!voteRepository.existsByUserAndAnswer(user, answer)) {
                Vote vote = new Vote();
                vote.setAnswer(answer);
                vote.setUser(user);
                vote.setIsDeleted(false);
                vote.setUuid(UUID.randomUUID().toString());
                voteRepository.save(vote);
            } else {
                // Check if user has voted on question
                Vote vote = voteRepository.findByUserAndAnswer(user, answer)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with uuidUser : "
                                + voteRequestAnswer.uuidUser() + " and Answer not found with uuidAnswer : " + voteRequestAnswer.uuidAnswer()));
                vote.setIsDeleted(false); // update isDeleted to false means remove vote
                voteRepository.save(vote);
            }
        }


        int countSignPlus = voteRepository.countByIsDeletedTrueAndAnswer(answer);
        int countSignMinus = voteRepository.countByIsDeletedFalseAndAnswer(answer);
        int totalSign = countSignPlus - countSignMinus;

        return JavaResponse.builder()
                .data(VoteResponse.builder()
                        .vote(totalSign)
                        .build())
                .build();
    }


    private JavaResponse<?> voteAndUnVoteQuestion(VoteRequestQuestion voteRequestQuestion, int code) {
        User user = userRepository.findByUuidAndIsDeletedTrue(voteRequestQuestion.uuidUser())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "User not found with uuid " + voteRequestQuestion.uuidUser()));

        Question question = questionRepository.findByUuidAndIsDeletedTrue(voteRequestQuestion.uuidQuestion())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Question not found with uuid " + voteRequestQuestion.uuidQuestion()));

        // User must login first
        if (user.getIsVerify() == null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User must login first to vote or unvote to question ");
        }
        if (code == 0) { // code = 0 => addVote
            if (voteRepository.existsByUserAndQuestionAndIsDeletedTrue(user, question)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "User already voted to question ");
            }
            // Check if the user has never voted on this question
            if (!voteRepository.existsByUserAndQuestion(user, question)) {
                Vote vote = new Vote();
                vote.setQuestion(question);
                vote.setUser(user);
                vote.setIsDeleted(true);
                vote.setUuid(UUID.randomUUID().toString());
                voteRepository.save(vote);
            } else {
                // Check if user has voted on question
                Vote vote = voteRepository.findByUserAndQuestion(user, question)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with uuidUser : "
                                + voteRequestQuestion.uuidUser() + " and Question not found with uuidQuestion : " + voteRequestQuestion.uuidQuestion()));
                vote.setIsDeleted(true); // update isDeleted to true means  vote
                voteRepository.save(vote);
            }
        } else { // code = 1 => unVote

            // Check if the user has previously removed their vote for the given question
            if (voteRepository.existsByUserAndQuestionAndIsDeletedFalse(user, question)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "User already remove vote to question ");
            }

            // Check if the user has never voted on this question
            if (!voteRepository.existsByUserAndQuestion(user, question)) {
                Vote vote = new Vote();
                vote.setQuestion(question);
                vote.setUser(user);
                vote.setIsDeleted(false);
                vote.setUuid(UUID.randomUUID().toString());
                voteRepository.save(vote);
            } else {
                // Check if user has voted on question
                Vote vote = voteRepository.findByUserAndQuestion(user, question)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with uuidUser : "
                                + voteRequestQuestion.uuidUser() + " and Question not found with uuidQuestion : " + voteRequestQuestion.uuidQuestion()));
                vote.setIsDeleted(false); // update isDeleted to false means remove vote
                voteRepository.save(vote);
            }
        }


        int countSignPlus = voteRepository.countByIsDeletedTrueAndQuestion(question);
        int countSignMinus = voteRepository.countByIsDeletedFalseAndQuestion(question);
        int totalSign = countSignPlus - countSignMinus;

        return JavaResponse.builder()
                .data(VoteResponse.builder()
                        .vote(totalSign)
                        .build())
                .build();
    }
}
