package com.finalProject.questionAndAnswer.feature.user;

import com.finalProject.questionAndAnswer.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User>  findByEmailAndIsDeletedTrue(String email);
    boolean existsByEmail(String email);
    Optional<User> findByUuidAndIsDeletedTrue(String uuid);
}
