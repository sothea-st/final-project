package com.finalProject.questionAndAnswer.feature.auth;

import com.finalProject.questionAndAnswer.feature.auth.dto.*;
import jakarta.mail.MessagingException;

public interface AuthService {
    RegisterResponse register(RegisterRequest registerRequest);


    void sendVerification(SendVerificationRequest sendVerificationRequest) throws MessagingException;


    void verify(VerifyRequest verifyRequest);


    LoginResponse login(LoginRequest loginRequest);


    AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
