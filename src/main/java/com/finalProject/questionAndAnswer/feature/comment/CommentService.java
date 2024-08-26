package com.finalProject.questionAndAnswer.feature.comment;

import com.finalProject.questionAndAnswer.feature.comment.dto.CommentAnswerRequest;
import com.finalProject.questionAndAnswer.feature.comment.dto.CommentRequest;
import com.finalProject.questionAndAnswer.feature.comment.dto.CommentResponse;
import com.finalProject.questionAndAnswer.feature.comment.dto.CommentUpdateRequest;
import com.finalProject.questionAndAnswer.response_success.JavaResponse;
import com.finalProject.questionAndAnswer.response_success.ResponseSuccess;

public interface CommentService {
    JavaResponse<?> addComment(CommentRequest commentRequest);

    ResponseSuccess deleteComment(String uuidComment);

    JavaResponse<?> updateComment(CommentUpdateRequest commentUpdateRequest, String uuidComment);
}
