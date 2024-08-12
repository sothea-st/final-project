package com.finalProject.questionAndAnswer.utils;

import lombok.Builder;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;

@Builder
public record ResponseLink(
        String detail,
        String href,
        String method
) {

    public static List<ResponseLink> links(String endpoint) {
        List<ResponseLink> links = new ArrayList<>();
        links.add(ResponseLink.builder()
                .detail("detail")
                .href(endpoint)
                .method("GET")
                .build());
        links.add(ResponseLink.builder()
                .detail("update")
                .href(endpoint)
                .method("PUT")
                .build());
        links.add(ResponseLink.builder()
                .detail("delete")
                .href(endpoint)
                .method("DELETE")
                .build());
        return links;
    }

}
