package com.finalProject.questionAndAnswer.feature.answer;

import com.finalProject.questionAndAnswer.domain.*;
import com.finalProject.questionAndAnswer.feature.answer.dto.AnswerRequest;
import com.finalProject.questionAndAnswer.feature.answer.dto.AnswerResponse;
import com.finalProject.questionAndAnswer.feature.image.ImageRepository;
import com.finalProject.questionAndAnswer.feature.image.dto.ImageResponse;
import com.finalProject.questionAndAnswer.feature.publicQuestion.dto.AuthorResponse;
import com.finalProject.questionAndAnswer.feature.publicQuestion.dto.CommentResponse;
import com.finalProject.questionAndAnswer.feature.question.QuestionRepository;
import com.finalProject.questionAndAnswer.feature.user.UserRepository;
import com.finalProject.questionAndAnswer.response_success.JavaResponse;
import com.finalProject.questionAndAnswer.response_success.ResponseSuccess;
import com.finalProject.questionAndAnswer.utils.JavaConstant;
import com.finalProject.questionAndAnswer.utils.ResponseLink;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AnswerServiceImp implements AnswerService {
    /**
     * Inject bean repository
     */
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;

    @Value("${base-url}")
    private String baseUrl;

    @Value("${file-upload.base-uri}")
    private String baseUrlImage;


    @Override
    public ResponseSuccess deleteAnswer(String uuidAnswer) {

        /**
         * validate answer exist or not
         */
        Answer answer = answerRepository.findByUuidAndIsDeletedTrue(uuidAnswer)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Answer Not Found with uuid " + uuidAnswer));

        answer.setIsDeleted(false);
        answerRepository.save(answer);
        return ResponseSuccess.builder().build();
    }

    @Override
    public JavaResponse<?> updateAnswer(AnswerRequest answerRequest, String uuidAnswer) {
        /**
         * validate user exist or not
         */
        User user = userRepository.findByUuidAndIsDeletedTrue(answerRequest.uuidUser())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User Not Found with uuid " + answerRequest.uuidUser()));
        /**
         * validate question exist or not
         */
        Question question = questionRepository.findByUuidAndIsDeletedTrue(answerRequest.uuidQuestion())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Question Not Found with uuid " + answerRequest.uuidQuestion()));

        /**
         * validate answer exist or not
         */
        Answer answer = answerRepository.findByUuidAndIsDeletedTrue(uuidAnswer)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Answer Not Found with uuid " + uuidAnswer));
        answer.setAnswer(answerRequest.answer());
        answer.setSnippedCode(answerRequest.snippedCode());
        answer.setQuestion(question);
        answer.setUser(user);
        answer.setIsDeleted(true);
        answerRepository.save(answer);

        System.out.println("answerRequest.images()  " + answerRequest.images());

        /**
         * save imageName to images table
         */
        if (answerRequest.images() != null) {
            answerRequest.images().forEach(image -> {
                Image image1 = new Image();
                image1.setAnswer(answer);
                image1.setImageName(image);
                image1.setIsDeleted(true);
                image1.setUuid(UUID.randomUUID().toString());
                imageRepository.save(image1);
            });
        }

        /**
         * get list image
         */
        List<Image> images = imageRepository.findByAnswerAndIsDeletedTrue(answer);
        answer.setImages(images);

        return JavaResponse.builder()
                .data(AnswerResponse.builder()
                        .answer(answer.getAnswer())
                        .snippedCode(answer.getSnippedCode())
                        .author(AuthorResponse.builder()
                                .name(answer.getUser().getUserName())
                                .uuidUser(answer.getUser().getUuid())
                                .profileImage(
                                        baseUrlImage.replace("upload", "images") + answer.getUser().getProfile()
                                )
                                .build())
                        .uuidAnswer(answer.getUuid())
                        .link(List.of(
                                ResponseLink.methodPut(baseUrl + "answer/" + answer.getUuid(), "endpoint for update"),
                                ResponseLink.methodDelete(baseUrl + "answer/" + answer.getUuid(), "endpoint for delete")
                        ))
                        .image(answer.getImages() != null ? answer.getImages().stream()
                                .map(img -> ImageResponse.builder()
                                        .name(img.getImageName())
                                        .uuidImage(img.getUuid())
                                        .url(baseUrlImage + img.getImageName())
                                        .link(ResponseLink.methodDelete(baseUrl + "images/" + img.getImageName(), "endpoint for delete image"))
                                        .build()).toList() : null
                        )
                        .build())
                .build();

    }

    @Override
    public JavaResponse<?> postAnswer(AnswerRequest answerRequest) {
        /**
         * validate user exist or not
         */
        User user = userRepository.findByUuidAndIsDeletedTrue(answerRequest.uuidUser())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User Not Found with uuid " + answerRequest.uuidUser()));
        /**
         * validate question exist or not
         */
        Question question = questionRepository.findByUuidAndIsDeletedTrue(answerRequest.uuidQuestion())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Question Not Found with uuid " + answerRequest.uuidQuestion()));

        Answer answer = new Answer();
        answer.setAnswer(answerRequest.answer());
        answer.setSnippedCode(answerRequest.snippedCode());
        answer.setQuestion(question);
        answer.setUser(user);
        answer.setIsDeleted(true);
        answer.setUuid(UUID.randomUUID().toString());
        answerRepository.save(answer);

        System.out.println("answerRequest.images()  " + answerRequest.images());

        /**
         * save imageName to images table
         */
        if (answerRequest.images() != null) {
            answerRequest.images().forEach(image -> {
                Image image1 = new Image();
                image1.setAnswer(answer);
                image1.setImageName(image);
                image1.setIsDeleted(true);
                image1.setUuid(UUID.randomUUID().toString());
                imageRepository.save(image1);
            });
        }

        /**
         * get list image
         */
        List<Image> images = imageRepository.findByAnswerAndIsDeletedTrue(answer);
        answer.setImages(images);

        return JavaResponse.builder()
                .data(AnswerResponse.builder()
                        .answer(answer.getAnswer())
                        .snippedCode(answer.getSnippedCode())
                        .postDate(JavaConstant.dateFormat(String.valueOf(answer.getCreatedAt())))
                        .author(AuthorResponse.builder()
                                .name(answer.getUser().getUserName())
                                .uuidUser(answer.getUser().getUuid())
                                .profileImage(
                                        baseUrlImage.replace("upload", "images") + answer.getUser().getProfile()
                                )
                                .build())
                        .uuidAnswer(answer.getUuid())
                        .link(List.of(
                                ResponseLink.methodPut(baseUrl + "answers/" + answer.getUuid(), "endpoint for update"),
                                ResponseLink.methodDelete(baseUrl + "answers/" + answer.getUuid(), "endpoint for delete")
                        ))
                        .image(answer.getImages() != null ? answer.getImages().stream()
                                .map(img -> ImageResponse.builder()
                                        .name(img.getImageName())
                                        .uuidImage(img.getUuid())
                                        .url(baseUrlImage + img.getImageName())
                                        .link(ResponseLink.methodDelete(baseUrl + "images/" + img.getImageName(), "endpoint for delete image"))
                                        .build()).toList() : null
                        )
                        .comments(List.of())
                        .build())
                .build();
    }
}












