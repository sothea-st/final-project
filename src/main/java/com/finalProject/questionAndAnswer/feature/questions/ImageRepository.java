package com.finalProject.questionAndAnswer.feature.questions;

import com.finalProject.questionAndAnswer.domain.Image;
import com.finalProject.questionAndAnswer.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image,Integer> {

    List<Image> findByQuestionAndIsDeletedTrue(Question question);


    Optional<Image> findByUuid(String uuid);
}
