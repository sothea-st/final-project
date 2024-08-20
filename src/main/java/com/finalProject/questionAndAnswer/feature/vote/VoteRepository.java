package com.finalProject.questionAndAnswer.feature.vote;

import com.finalProject.questionAndAnswer.domain.Answer;
import com.finalProject.questionAndAnswer.domain.Question;
import com.finalProject.questionAndAnswer.domain.User;
import com.finalProject.questionAndAnswer.domain.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote,Integer> {
    int countByIsDeletedTrueAndQuestion(Question question);
    int countByIsDeletedTrueAndAnswer(Answer answer);
    int countByIsDeletedFalseAndQuestion(Question question);
    int countByIsDeletedFalseAndAnswer(Answer answer);
    boolean existsByUserAndQuestionAndIsDeletedTrue(User user , Question question);
    boolean existsByUserAndAnswerAndIsDeletedTrue(User user , Answer answer);
    boolean existsByUserAndQuestionAndIsDeletedFalse(User user , Question question);
    boolean existsByUserAndAnswerAndIsDeletedFalse(User user , Answer answer);
    boolean existsByUserAndQuestion(User user , Question question);
    boolean existsByUserAndAnswer(User user , Answer answer);
    Optional<Vote> findByUserAndQuestion(User user , Question question);
    Optional<Vote> findByUserAndAnswer(User user , Answer answer);

}
