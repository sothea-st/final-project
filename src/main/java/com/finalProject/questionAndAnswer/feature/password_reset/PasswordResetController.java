package com.finalProject.questionAndAnswer.feature.password_reset;

import com.finalProject.questionAndAnswer.feature.password_reset.dto.PasswordResetRequest;
import com.finalProject.questionAndAnswer.response_success.JavaResponse;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/password-reset")
@RequiredArgsConstructor
public class PasswordResetController {
    // inject bean service
    private final PasswordResetService passwordResetService;

    @PostMapping
    JavaResponse<?> passwordReset(@Valid @RequestBody PasswordResetRequest passwordResetRequest) throws MessagingException {
        return passwordResetService.passwordReset(passwordResetRequest);
    }

}
