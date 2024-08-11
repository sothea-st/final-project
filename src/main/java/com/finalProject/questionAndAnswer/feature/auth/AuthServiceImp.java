package com.finalProject.questionAndAnswer.feature.auth;

import com.finalProject.questionAndAnswer.domain.Role;
import com.finalProject.questionAndAnswer.domain.User;
import com.finalProject.questionAndAnswer.domain.UserVerification;
import com.finalProject.questionAndAnswer.feature.auth.dto.*;
import com.finalProject.questionAndAnswer.feature.user.RoleRepository;
import com.finalProject.questionAndAnswer.feature.user.UserRepository;
import com.finalProject.questionAndAnswer.feature.user.UserVerificationRepository;
import com.finalProject.questionAndAnswer.security.CustomUserDetails;
import com.finalProject.questionAndAnswer.security.UserDetailsServiceImp;
import com.finalProject.questionAndAnswer.utils.RandomUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImp implements AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JavaMailSender javaMailSender;
    private final PasswordEncoder passwordEncoder;
    private final UserVerificationRepository userVerificationRepository;
    private final DaoAuthenticationProvider daoAuthenticationProvider;
    private final JwtEncoder accessTokenJwtEncoder;
    private final JwtEncoder refreshTokenJwtEncoder;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    @Value("${spring.mail.username}")
    private String emailAdmin;

    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {

        // validation
        if (userRepository.existsByEmail(registerRequest.email())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Email already in use"
            );
        }

        if (!registerRequest.password().equals(registerRequest.confirmPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Confirm password does not match");
        }

        User user = new User();
        user.setUserName(registerRequest.userName());
        user.setPassword(passwordEncoder.encode(registerRequest.password()));
        user.setEmail(registerRequest.email());
        user.setIsDeleted(true);
        user.setUuid(UUID.randomUUID().toString());
        Role roleUser = roleRepository.findRoleUser();
        Role roleAuthor = roleRepository.findRoleAuthor();
        List<Role> roles = List.of(roleUser, roleAuthor);

        user.setRoles(roles);
        userRepository.save(user);


        return RegisterResponse.builder()
                .message("Registered Successfully")
                .email("Email : ".concat(user.getEmail()))
                .build();
    }

    @Override
    public void sendVerification(SendVerificationRequest sendVerificationRequest) throws MessagingException {

        // validate email
        User user = userRepository.findByEmailAndIsDeletedTrue(sendVerificationRequest.email())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Email has not been found"));


        String random6Digits = RandomUtil.randomUtil6Digit();
        UserVerification userVerifications = userVerificationRepository.findByUser(user);
        if (userVerifications == null) {
            UserVerification userVerification = new UserVerification();
            userVerification.setUser(user);
            userVerification.setExpirationTime(LocalTime.now().plusMinutes(1));
            userVerification.setVerifyCode(random6Digits);
            userVerificationRepository.save(userVerification);
        } else {
            userVerifications.setExpirationTime(LocalTime.now().plusMinutes(1));
            userVerifications.setVerifyCode(random6Digits);
            userVerificationRepository.save(userVerifications);
        }


        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message);
        messageHelper.setTo(sendVerificationRequest.email());
        messageHelper.setFrom(emailAdmin);
        messageHelper.setSubject("User verification");
        messageHelper.setText(random6Digits);
        javaMailSender.send(message);
    }

    @Override
    public void verify(VerifyRequest verifyRequest) {
        User user = userRepository.findByEmailAndIsDeletedTrue(verifyRequest.email())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Email has not been found"));

        UserVerification userVerification = userVerificationRepository
                .findByUserAndVerifyCode(user, verifyRequest.verificationCode())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User verification incorrect"));

        if (LocalTime.now().isAfter(userVerification.getExpirationTime())) {
            userVerificationRepository.delete(userVerification);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Expired Verification code");
        }

        user.setIsVerify(true);
        userRepository.save(user);
        userVerificationRepository.delete(userVerification);
    }


    @Override
    public AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.refreshToken();
        // Authenticate client with refresh token
        // BearerTokenAuthenticationToken use to create authentication object type JWT
        Authentication auth = new BearerTokenAuthenticationToken(refreshToken);
        auth = jwtAuthenticationProvider.authenticate(auth);

        Jwt jwt = (Jwt) auth.getPrincipal();

        Instant now = Instant.now();
        JwtClaimsSet jwtAccessClaimsSet = JwtClaimsSet.builder()
                .id(jwt.getId())
                .subject("Access APIs")
                .issuer(jwt.getId())
                .issuedAt(now)
                .expiresAt(now.plus(30, ChronoUnit.SECONDS))
                .audience(jwt.getAudience())
                .claim("scope", jwt.getClaimAsString("scope"))
                .build();

        String accessToken = accessTokenJwtEncoder
                .encode(JwtEncoderParameters.from(jwtAccessClaimsSet))
                .getTokenValue();

        // Get expiration of refresh token
        Instant expiresAt = jwt.getExpiresAt();
        long remainingDays = Duration.between(now, expiresAt).toDays();

        if (remainingDays <= 1) {
            JwtClaimsSet jwtRefreshClaimsSet = JwtClaimsSet.builder()
                    .id(auth.getName())
                    .subject("Refresh Token")
                    .issuer(auth.getName())
                    .issuedAt(now)
                    .expiresAt(now.plus(7, ChronoUnit.DAYS))
                    .audience(List.of("Mobile APP", "Desktop", "Vue.js"))
                    .claim("scope", jwt.getClaimAsString("scope"))
                    .build();
            refreshToken = refreshTokenJwtEncoder
                    .encode(JwtEncoderParameters.from(jwtRefreshClaimsSet))
                    .getTokenValue();
        }

        return AuthResponse.builder()
                .tokenType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }


    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        /**
         *          Authenticate client with username (phoneNumber) and password
         *          use class UserPasswordAuthenticationToken to check user secure or not , authenticate or not
         *          success return object Authentication fail return null
         *          when user login UsernamePasswordAuthenticationToken automatic invoke bean DaoAuthenticationProvider
         *          Authentication auth =new UsernamePasswordAuthenticationToken(loginRequest.email() , loginRequest.password());
         *          auth =daoAuthenticationProvider.authenticate(auth);
         */

        Authentication auth = new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password());

        auth = daoAuthenticationProvider.authenticate(auth); // method authenticate() auto invoke security process


        // scope is required add to payload
        String scope = auth.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));


        //        ************************ access token ************************
        Instant now = Instant.now();
        log.info("********************** success : ********************** " + auth.getPrincipal());
        // Generate JWT Token by JwtEncoder
        // there are 2 steps
        // step 1 . define claimSets ( payload )
        JwtClaimsSet jwtAccessClaimsSet = JwtClaimsSet.builder()
                .id(auth.getName())
                .subject("Access APIS")
                .issuer(auth.getName())
                .issuedAt(now)
                .expiresAt(now.plus(30, ChronoUnit.MINUTES))
                .audience(List.of("Mobile APP", "Desktop", "Vue.js"))
                .claim("scope", scope)
                .build();
        // step 2. Generate Token
        String accessToken = accessTokenJwtEncoder
                .encode(JwtEncoderParameters.from(jwtAccessClaimsSet))
                .getTokenValue();
        log.info("*************** success generate token : *************** " + accessToken);


//        ************************ refresh token ************************
        JwtClaimsSet jwtRefreshClaimsSet = JwtClaimsSet.builder()
                .id(auth.getName())
                .subject("Refresh Token")
                .issuer(auth.getName())
                .expiresAt(now.plus(7, ChronoUnit.DAYS))
                .issuedAt(now.plus(30, ChronoUnit.MINUTES))
                .audience(List.of("Mobile APP", "Desktop", "Vue.js"))
                .claim("scope", scope)
                .build();

        String refreshToken = refreshTokenJwtEncoder
                .encode(JwtEncoderParameters
                        .from(jwtRefreshClaimsSet))
                .getTokenValue();


        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();

//        if (auth != null && auth.isAuthenticated()) {
//            Object principal = auth.getPrincipal();
//            if (principal instanceof CustomUserDetails) {
//                userDetails = (CustomUserDetails) principal;
//                String uuid = userDetails.getUser().getUuid();
//
//                Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
//                for( GrantedAuthority authority : authorities ) {
//                    System.out.println("ggggg=  " + authority.getAuthority());
//                }
//
//                // Use these details as needed
//                System.out.println("Username: " + uuid);
//                System.out.println("Authorities: " + authorities);
//            } else {
//                // Handle cases where the principal is not an instance of UserDetails
//                System.out.println("Authenticated principal is not of type UserDetails");
//            }
//        } else {
//            System.out.println("Authentication failed or was not performed");
//        }

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();


        return LoginResponse.builder()
                .email(userDetails.getUser().getEmail())
                .profile(userDetails.getUser().getProfile())
                .userName(userDetails.getUser().getUserName())
                .uuidUser(userDetails.getUser().getUuid())
                .token(AuthResponse.builder()
                        .tokenType("Bearer")
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build())
                .roles(roles)
                .build();
    }


}
