package com.finalProject.questionAndAnswer.feature.vote.dto;

import lombok.Builder;

@Builder
public record VoteResponse(
        Integer vote
) {
}
