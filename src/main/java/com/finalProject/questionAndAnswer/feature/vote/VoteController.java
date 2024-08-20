package com.finalProject.questionAndAnswer.feature.vote;

import com.finalProject.questionAndAnswer.feature.vote.dto.VoteRequestAnswer;
import com.finalProject.questionAndAnswer.feature.vote.dto.VoteRequestQuestion;
import com.finalProject.questionAndAnswer.response_success.JavaResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/votes")
@RequiredArgsConstructor
public class VoteController {
    // inject bean service
    private final VoteService voteService;

    @PostMapping("/question")
    JavaResponse<?> postVote(@Valid @RequestBody VoteRequestQuestion voteRequestQuestion) {
        return voteService.addVoteQuestion(voteRequestQuestion);
    }

    @PostMapping("/remove-question")
    JavaResponse<?> upVote(@Valid @RequestBody VoteRequestQuestion voteRequestQuestion) {
        return voteService.unVoteQuestion(voteRequestQuestion);
    }

    @PostMapping("/answer")
    JavaResponse<?> postVoteAnswer(@Valid @RequestBody VoteRequestAnswer voteRequestAnswer) {
        return voteService.addVoteAnswer(voteRequestAnswer);
    }

    @PostMapping("/remove-answer")
    JavaResponse<?> upVoteAnswer(@Valid @RequestBody VoteRequestAnswer voteRequestAnswer) {
        return voteService.unVoteAnswer(voteRequestAnswer);
    }

}
