package com.finalProject.questionAndAnswer.feature.comment;

import com.finalProject.questionAndAnswer.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    Optional<Comment> findByUuidAndIsDeletedTrue(String uuid);

}
