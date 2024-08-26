package com.finalProject.questionAndAnswer.security;

import com.finalProject.questionAndAnswer.domain.User;
import com.finalProject.questionAndAnswer.feature.user.UserRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserDetailsServiceImp implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmailAndIsDeletedTrue(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email has not been found"));

        // verify email if null throw error
//        verifyEmail(user.getIsVerify());

        log.info("********************* User : {}" , user.getEmail() );
        CustomUserDetails customUserDetails = new CustomUserDetails();
        customUserDetails.setUser(user);
        return customUserDetails;
    }

    private void verifyEmail(Boolean isVerify) {
        if( isVerify == null ) throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,"Please verify your email"
        );
    }
}
