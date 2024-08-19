package com.finalProject.questionAndAnswer.feature.vote;

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

    @PostMapping
    JavaResponse<?> postVote(@Valid @RequestBody VoteRequestQuestion voteRequestQuestion) {
        return voteService.addVoteQuestion(voteRequestQuestion);
    }

}
