package com.finalProject.questionAndAnswer.feature.auth;

import com.finalProject.questionAndAnswer.feature.auth.dto.*;
import com.finalProject.questionAndAnswer.response_success.JavaResponse;
import jakarta.mail.MessagingException;

public interface AuthService {
    RegisterResponse register(RegisterRequest registerRequest);


    void sendVerification(SendVerificationRequest sendVerificationRequest) throws MessagingException;


    LoginResponse verify(VerifyRequest verifyRequest);


    JavaResponse<?> login(LoginRequest loginRequest);


    AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest);


}
