package com.finalProject.questionAndAnswer.response_success;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseSuccess {
    @Builder.Default
    private int status=200;

    @Builder.Default
    private String msg="success";
}
