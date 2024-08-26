package com.finalProject.questionAndAnswer.feature.auth;

import com.finalProject.questionAndAnswer.feature.auth.dto.*;
import com.finalProject.questionAndAnswer.response_success.JavaResponse;
import com.finalProject.questionAndAnswer.response_success.ResponseSuccess;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    ResponseSuccess register(@Valid @RequestBody RegisterRequest registerRequest) {
          authService.register(registerRequest);
        return ResponseSuccess.builder().build();
    }

    @PostMapping("/send-verification")
    ResponseSuccess sendVerification(@Valid @RequestBody SendVerificationRequest sendVerificationRequest) throws MessagingException {
        authService.sendVerification(sendVerificationRequest);
        return ResponseSuccess.builder().build();
    }

    @PostMapping("/resend-verification")
    ResponseSuccess resendVerification(@Valid @RequestBody SendVerificationRequest sendVerificationRequest) throws MessagingException {
        authService.sendVerification(sendVerificationRequest);
        return ResponseSuccess.builder().build();
    }

    @PostMapping("/verify")
    LoginResponse verify(@Valid @RequestBody VerifyRequest verifyRequest) {
        System.out.println("ddddddddddddddddddd");
        return authService.verify(verifyRequest);
    }

    @PostMapping("/login")
    JavaResponse<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/refresh-token")
    AuthResponse refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authService.refreshToken(refreshTokenRequest);
    }

}
