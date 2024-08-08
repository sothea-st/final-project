package com.finalProject.questionAndAnswer.initData;

import com.finalProject.questionAndAnswer.constant.UUIDConstant;
import com.finalProject.questionAndAnswer.domain.Role;
import com.finalProject.questionAndAnswer.domain.User;
import com.finalProject.questionAndAnswer.feature.user.RoleRepository;
import com.finalProject.questionAndAnswer.feature.user.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DataInit {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @PostConstruct
    public void initData(){


        if( userRepository.count() == 0 ) {

            Role role = new Role();
            role.setName("USER");
            roleRepository.save(role);

            Role role2 = new Role();
            role2.setName("ADMIN");
            roleRepository.save(role2);

            Role role3 = new Role();
            role3.setName("AUTHOR");
            roleRepository.save(role3);

            User user = new User();
            user.setUserName("sothea");
            user.setEmail("sothea@gmail.com");
            user.setPassword(passwordEncoder.encode("12345"));
            user.setUuid(UUID.randomUUID().toString());
            user.setIsDeleted(true);
            user.setRoles(List.of(role,role2,role3));

            User user1 = new User();
            user1.setUserName("sonita");
            user1.setEmail("sonita@gmail.com");
            user1.setPassword(passwordEncoder.encode("12345"));
            user1.setUuid(UUID.randomUUID().toString());
            user1.setIsDeleted(true);
            user1.setRoles(List.of(role,role2,role3));

            userRepository.saveAll(List.of(user,user1));
        }

    }

}

