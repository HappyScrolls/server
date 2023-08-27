package com.HappyScrolls.dto;


import com.HappyScrolls.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

public class TagDTO {

    @Builder
    @Getter
    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class Request {
        private String body;

    }

    @Builder
    @Getter
    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class ListRequest {
        private List<String> tags;

    }

    @Builder
    @Getter
    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private String body;
    }
}