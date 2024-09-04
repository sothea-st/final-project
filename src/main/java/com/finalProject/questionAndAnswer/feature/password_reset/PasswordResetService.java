package com.finalProject.questionAndAnswer.feature.password_reset;

import com.finalProject.questionAndAnswer.feature.password_reset.dto.PasswordResetRequest;
import com.finalProject.questionAndAnswer.response_success.JavaResponse;
import jakarta.mail.MessagingException;

public interface PasswordResetService {
    JavaResponse<?> passwordReset(PasswordResetRequest passwordResetRequest) throws MessagingException;
}
