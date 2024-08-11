package com.finalProject.questionAndAnswer.feature.questions;

import com.finalProject.questionAndAnswer.domain.Question;
import com.finalProject.questionAndAnswer.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question,Integer> {
    Page<Question> findByIsDeletedTrueAndUser(User user, Pageable pageable);
}
