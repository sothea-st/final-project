package com.finalProject.questionAndAnswer.feature.user;

import com.finalProject.questionAndAnswer.domain.User;
import com.finalProject.questionAndAnswer.domain.UserVerification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

public interface UserVerificationRepository extends JpaRepository<UserVerification,Integer> {

    Optional<UserVerification> findByUserAndVerifyCode(User user,String verificationCode);

    UserVerification findByUser(User user);

    long countByUser(User user);

}
