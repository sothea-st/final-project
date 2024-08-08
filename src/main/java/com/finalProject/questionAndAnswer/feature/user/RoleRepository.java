package com.finalProject.questionAndAnswer.feature.user;

import com.finalProject.questionAndAnswer.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoleRepository extends JpaRepository<Role,Integer> {

    @Query(value = "select r from Role as r where r.name = 'USER'")
    Role findRoleUser();

    @Query(value = "select r from Role as r where r.name = 'ADMIN'")
    Role findRoleAdmin();

    @Query(value = "select r from Role as r where r.name = 'AUTHOR'")
    Role findRoleAuthor();

}
