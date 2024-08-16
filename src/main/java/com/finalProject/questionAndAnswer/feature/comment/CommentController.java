package com.finalProject.questionAndAnswer.feature.comment;

import com.finalProject.questionAndAnswer.feature.comment.dto.CommentRequest;
import com.finalProject.questionAndAnswer.response_success.JavaResponse;
import com.finalProject.questionAndAnswer.response_success.ResponseSuccess;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {
    /*
        inject bean service
     */
    private final CommentService commentService;


    @PostMapping
    JavaResponse<?> addComment(@Valid @RequestBody CommentRequest commentRequest) {
        return commentService.addComment(commentRequest);
    }

    @DeleteMapping("/{uuidComment}")
    ResponseSuccess deleteComment(@PathVariable String uuidComment) {
        return commentService.deleteComment(uuidComment);
    }

    @PutMapping("/{uuidComment}")
    JavaResponse<?> updateComment(@Valid @RequestBody CommentRequest commentRequest , @PathVariable String uuidComment) {
        return commentService.updateComment(commentRequest,uuidComment);
    }


}
