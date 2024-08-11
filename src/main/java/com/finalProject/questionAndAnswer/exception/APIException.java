package com.finalProject.questionAndAnswer.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@RestControllerAdvice
@Slf4j
public class APIException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    Map<?, ?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldResponse> fieldResponses = new ArrayList<>();

        e.getFieldErrors().forEach(fieldError -> fieldResponses.add(
                FieldResponse.builder()
                        .field(fieldError.getField())
                        .detail(fieldError.getDefaultMessage())
                        .build()
        ));

        FieldError<?> fieldError = FieldError.builder()
                .status(e.getStatusCode().value())
                .reason(fieldResponses)
                .build();

        return Map.of("error", fieldError);
    }


    @ExceptionHandler(ResponseStatusException.class)
    Map<?, ?> handleResponseStatusException(ResponseStatusException e) {

        FieldError<?> fieldError = FieldError.builder()
                .status(e.getStatusCode().value())
                .reason(e.getReason())
                .build();

        return Map.of("error", fieldError);
    }

//    @ExceptionHandler(BadCredentialsException.class)
//    public ResponseEntity<Map<String, Object>> handleBadCredentialsException(BadCredentialsException e) {
//        log.error("Authentication failed: ", e);
//
//        FieldError<?> fieldError = FieldError.builder()
//                .status(HttpStatus.UNAUTHORIZED.value())
//                .reason("email or password is incorrect")
//                .build();
//
//        return new ResponseEntity<>(Map.of("error", fieldError), HttpStatus.UNAUTHORIZED);
//    }d




}
