package com.finalProject.questionAndAnswer.feature.answer;

import com.finalProject.questionAndAnswer.domain.Answer;
import com.finalProject.questionAndAnswer.domain.Question;
import com.finalProject.questionAndAnswer.domain.User;
import com.finalProject.questionAndAnswer.domain.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
    Optional<Answer> findByUuidAndIsDeletedTrue(String uuidAnswer);

    Optional<Vote> findByUserAndAnswer(User user , Answer answer);
}
