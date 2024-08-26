package com.finalProject.questionAndAnswer.feature.comment;

import com.finalProject.questionAndAnswer.domain.Answer;
import com.finalProject.questionAndAnswer.domain.Comment;
import com.finalProject.questionAndAnswer.domain.Question;
import com.finalProject.questionAndAnswer.domain.User;
import com.finalProject.questionAndAnswer.feature.answer.AnswerRepository;
import com.finalProject.questionAndAnswer.feature.comment.dto.CommentAnswerRequest;
import com.finalProject.questionAndAnswer.feature.comment.dto.CommentRequest;
import com.finalProject.questionAndAnswer.feature.comment.dto.CommentResponse;
import com.finalProject.questionAndAnswer.feature.comment.dto.CommentUpdateRequest;
import com.finalProject.questionAndAnswer.feature.question.QuestionRepository;
import com.finalProject.questionAndAnswer.feature.user.UserRepository;
import com.finalProject.questionAndAnswer.response_success.JavaResponse;
import com.finalProject.questionAndAnswer.response_success.ResponseSuccess;
import com.finalProject.questionAndAnswer.utils.JavaConstant;
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
    private final AnswerRepository answerRepository;

    /**
     * variable not found
     */
    private String questionNotFound = "Question not found with uuid : ";
    private String answertNotFound = "Answer not found with uuid : ";
    private String commentNotFound = "Comment not found with uuid : ";


    @Value("${file-upload.base-uri}")
    private String baseUrlImage;

    @Value("${base-url.read-image}")
    private String baseUrlImagePublic;


    @Override
    public JavaResponse<?> updateComment(CommentUpdateRequest commentUpdateRequest, String uuidComment) {

        // Create a new comment
        Comment comment = commentRepository.findByUuidAndIsDeletedTrue(uuidComment)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        commentNotFound + uuidComment));


        comment.setComment(commentUpdateRequest.comment());
        commentRepository.save(comment);


        // Build the response
        return JavaResponse.builder()
                .data(CommentResponse.builder()
                        .comment(comment.getComment())
//                        .profile(baseUrlImagePublic + user.getProfile())
//                        .userComment(user.getUserName())
//                        .uuidComment(comment.getUuid())
//                        .postDate(JavaConstant.dateFormat(String.valueOf(comment.getCreatedAt())))
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
        return mapToCommentResponse(commentRequest);
    }

    private JavaResponse<?> mapToCommentResponse(CommentRequest commentRequest) {

        if (commentRequest.uuidQuestion() == null && commentRequest.uuidAnswer() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "the field uuidQuestion or uuidAnswer are required one of them");
        }

        // Create a new comment
        Comment comment = new Comment();

        if (commentRequest.uuidQuestion() != null) {
            // Validate and retrieve the question
            Question question = questionRepository.findByUuidAndIsDeletedTrue(commentRequest.uuidQuestion())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                            questionNotFound + commentRequest.uuidQuestion()));
            comment.setQuestion(question);
        }
        if(commentRequest.uuidAnswer() != null) {
            Answer answer = answerRepository.findByUuidAndIsDeletedTrue(commentRequest.uuidAnswer())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                            answertNotFound + commentRequest.uuidAnswer()));
            comment.setAnswer(answer);
        }

        comment.setComment(commentRequest.comment());
        comment.setUuid(UUID.randomUUID().toString());
        comment.setIsDeleted(true);


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
                        .profile(baseUrlImagePublic + user.getProfile())
                        .userComment(user.getUserName())
                        .uuidComment(comment.getUuid())
                        .postDate(JavaConstant.dateFormat(String.valueOf(comment.getCreatedAt())))
                        .build())
                .build();
    }

}
