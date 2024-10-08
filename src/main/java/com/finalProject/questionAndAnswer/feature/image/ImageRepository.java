package com.finalProject.questionAndAnswer.feature.image;

import com.finalProject.questionAndAnswer.domain.Answer;
import com.finalProject.questionAndAnswer.domain.Image;
import com.finalProject.questionAndAnswer.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image,Integer> {

    List<Image> findByQuestionAndIsDeletedTrue(Question question);
    List<Image> findByAnswerAndIsDeletedTrue(Answer answer);

    Optional<Image> findByUuid(String uuid);

    Optional<Image> findByImageName(String imageName);

    boolean existsByImageName(String imageName);

}
