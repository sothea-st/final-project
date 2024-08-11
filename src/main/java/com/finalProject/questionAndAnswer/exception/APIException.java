package com.finalProject.questionAndAnswer.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;




@RestControllerAdvice
@Slf4j
public class APIException {

    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxSize;


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


    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
    Map<?, ?> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        FieldError<?> fieldError = FieldError.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .reason(e.getMessage() + ":" +maxSize )
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
