package com.finalProject.questionAndAnswer.feature.vote;

import com.finalProject.questionAndAnswer.feature.vote.dto.VoteRequestAnswer;
import com.finalProject.questionAndAnswer.feature.vote.dto.VoteRequestQuestion;
import com.finalProject.questionAndAnswer.response_success.JavaResponse;

public interface VoteService {
    JavaResponse<?> addVoteQuestion(VoteRequestQuestion voteRequestQuestion);
    JavaResponse<?> unVoteQuestion(VoteRequestQuestion voteRequestQuestion);
    JavaResponse<?> unVoteAnswer(VoteRequestAnswer voteRequestAnswer);

    JavaResponse<?> addVoteAnswer(VoteRequestAnswer voteRequestAnswer);
}
