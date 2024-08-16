package com.finalProject.questionAndAnswer.feature.comment;

import com.finalProject.questionAndAnswer.domain.Comment;
import com.finalProject.questionAndAnswer.domain.Question;
import com.finalProject.questionAndAnswer.domain.User;
import com.finalProject.questionAndAnswer.feature.comment.dto.CommentRequest;
import com.finalProject.questionAndAnswer.feature.comment.dto.CommentResponse;
import com.finalProject.questionAndAnswer.feature.question.QuestionRepository;
import com.finalProject.questionAndAnswer.feature.user.UserRepository;
import com.finalProject.questionAndAnswer.response_success.JavaResponse;
import com.finalProject.questionAndAnswer.response_success.ResponseSuccess;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentServiceImp implements CommentService {
    /**
     * Inject bean repository
     */
    private final CommentRepository commentRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    /**
     * variable not found
     */
    private String questionNotFound = "Question not found with uuid : ";
    private String commentNotFound = "Comment not found with uuid : ";


    @Value("${file-upload.base-uri}")
    private String baseUrlImage;


    @Override
    public JavaResponse<?> updateComment(CommentRequest commentRequest, String uuidComment) {
        // Validate and retrieve the question
        questionRepository.findByUuidAndIsDeletedTrue(commentRequest.uuidQuestion())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        questionNotFound + commentRequest.uuidQuestion()));
// Retrieve the existing user by their uuid
        User user = userRepository.findByUuidAndIsDeletedTrue(commentRequest.uuidUser())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "User not found with uuid: " + commentRequest.uuidUser()));

        // Create a new comment
        Comment comment = commentRepository.findByUuidAndIsDeletedTrue(uuidComment)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        commentNotFound + uuidComment));


        comment.setComment(commentRequest.comment());
        commentRepository.save(comment);


        // Build the response
        return JavaResponse.builder()
                .data(CommentResponse.builder()
                        .comment(comment.getComment())
                        .profile(baseUrlImage.replace("upload", "images") + user.getProfile())
                        .userComment(user.getUserName())
                        .uuidComment(comment.getUuid())
                        .build())
                .build();

    }

    @Override
    public ResponseSuccess deleteComment(String uuidComment) {

        // validate comment
        Comment comment = commentRepository.findByUuidAndIsDeletedTrue(uuidComment)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        commentNotFound + uuidComment));

        comment.setIsDeleted(false);
        commentRepository.save(comment);
        return ResponseSuccess.builder().build();
    }

    @Override
    public JavaResponse<?> addComment(CommentRequest commentRequest) {

        // Validate and retrieve the question
        Question question = questionRepository.findByUuidAndIsDeletedTrue(commentRequest.uuidQuestion())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        questionNotFound + commentRequest.uuidQuestion()));

        // Create a new comment
        Comment comment = new Comment();
        comment.setComment(commentRequest.comment());
        comment.setUuid(UUID.randomUUID().toString());
        comment.setIsDeleted(true);
        comment.setQuestion(question);

        // Retrieve the existing user by their uuid
        User user = userRepository.findByUuidAndIsDeletedTrue(commentRequest.uuidUser())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "User not found with uuid: " + commentRequest.uuidUser()));

        // Add the comment to the user's list of comments
        user.getComments().add(comment);
        comment.setUser(user);


        // Save the comment and update the user
        commentRepository.save(comment);
        userRepository.save(user);

        // Build the response
        return JavaResponse.builder()
                .data(CommentResponse.builder()
                        .comment(comment.getComment())
                        .profile(baseUrlImage.replace("upload", "images") + user.getProfile())
                        .userComment(user.getUserName())
                        .uuidComment(comment.getUuid())
                        .build())
                .build();
    }

}
