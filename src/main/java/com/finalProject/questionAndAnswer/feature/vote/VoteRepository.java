package com.finalProject.questionAndAnswer.feature.vote;

import com.finalProject.questionAndAnswer.domain.Question;
import com.finalProject.questionAndAnswer.domain.User;
import com.finalProject.questionAndAnswer.domain.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote,Integer> {
    int countByIsDeletedTrue();
    int countByIsDeletedFalse();
    Vote findByUserAndQuestion(User user , Question question);
}
