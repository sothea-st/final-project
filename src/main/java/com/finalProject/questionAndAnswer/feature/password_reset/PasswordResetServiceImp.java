package com.finalProject.questionAndAnswer.feature.password_reset;

import com.finalProject.questionAndAnswer.domain.User;
import com.finalProject.questionAndAnswer.feature.password_reset.dto.PasswordResetRequest;
import com.finalProject.questionAndAnswer.feature.password_reset.dto.PasswordResponse;
import com.finalProject.questionAndAnswer.feature.user.UserRepository;
import com.finalProject.questionAndAnswer.response_success.JavaResponse;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetServiceImp implements PasswordResetService {
    // inject bean repository
    private final UserRepository userRepository;
    private final JavaMailSender javaMailSender;

    // variable not found
    private String emailNotFound = "The email does not exist in system.";

    // variable value from application.property
    @Value("${spring.mail.username}")
    private String emailAdmin;

    @Value("${base-url}")
    private String baseUrl;


    @Override
    public JavaResponse<?> passwordReset(PasswordResetRequest passwordResetRequest) throws MessagingException {

        // validate email
        User user = userRepository.findByEmailAndIsDeletedTrue(passwordResetRequest.email())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, emailNotFound));

        String uuidResetPassword = UUID.randomUUID().toString();
        user.setResetPassword(uuidResetPassword);

        // Use LocalDateTime for expiration time instead of LocalTime
        LocalDateTime expirationDateTime = LocalDateTime.now().plusMinutes(30);
        System.out.println("Current time: " + expirationDateTime.toString());
        user.setExpirationTime(expirationDateTime.toLocalTime()); // If you must save only the time

        userRepository.save(user);

        String resetUrl = baseUrl + "reset-password/" + uuidResetPassword;

        // send message
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom(emailAdmin);
        messageHelper.setTo(passwordResetRequest.email());
        messageHelper.setSubject("Click the link to reset your password: " + resetUrl);
        javaMailSender.send(message);

        return JavaResponse.builder()
                .data(PasswordResponse.builder()
                        .url(resetUrl)
                        .build())
                .build();
    }
}
