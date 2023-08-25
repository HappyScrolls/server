package com.HappyScrolls.dto;

import com.HappyScrolls.entity.Article;
import lombok.*;

import java.util.List;

public class ArticleDTO {


    @Builder
    @Getter
    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class Request{
        private String title;
        private String body;

        private List<TagDTO.Request> tags;

        public Article toEntity() {
            return Article.builder()
                    .title(this.title)
                    .body(this.body)
                    .build();
        }

    }


    @Builder
    @Getter
    @RequiredArgsConstructor
    @AllArgsConstructor
    @Data
    public static class DetailResponse {
        private Long id;
        private String title;
        private String body;
        private List<TagDTO.Response> tags;



    }
    @Builder
    @Getter
    @RequiredArgsConstructor
    @AllArgsConstructor
    @Data
    public static class ListResponse {
        private Long id;
        private String title;

        private String member;

    }

    @Builder
    @Getter
    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class Edit {
        private Long id;
        private String title;
        private String body;
    }
}
