package com.finalProject.questionAndAnswer.feature.answer;

import com.finalProject.questionAndAnswer.domain.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
}
