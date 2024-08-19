package com.finalProject.questionAndAnswer.utils;

import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

@Builder
public record ResponseLink(
        String rel,
        String endpoint,
        String method
) {

    public static List<ResponseLink> links(String endpoint) {
        List<ResponseLink> links = new ArrayList<>();
        links.add(ResponseLink.builder()
                .rel("self")
                .endpoint(endpoint)
                .method("GET")
                .build());
        links.add(ResponseLink.builder()
                .rel("update")
                .endpoint(endpoint)
                .method("PUT")
                .build());
        links.add(ResponseLink.builder()
                .rel("delete")
                .endpoint(endpoint)
                .method("DELETE")
                .build());
        return links;
    }

    public static ResponseLink methodDelete(String endpoint,String detail) {
        return ResponseLink.builder()
                .rel(detail)
                .endpoint(endpoint)
                .method("DELETE")
                .build();
    }

    public static ResponseLink methodPut(String endpoint,String detail) {
        return ResponseLink.builder()
                .rel(detail)
                .endpoint(endpoint)
                .method("PUT")
                .build();
    }

    public static ResponseLink methodGet(String endpoint,String detail) {
        return ResponseLink.builder()
                .rel(detail)
                .endpoint(endpoint)
                .method("GET")
                .build();
    }

}
