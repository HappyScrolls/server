package com.HappyScrolls.dto;

import com.HappyScrolls.entity.Article;
import lombok.Builder;
import lombok.Data;

public class ArticleDTO {


    @Builder
    @Data
    public static class Request{
        private String title;
        private String body;

        public Article toEntity() {
            return Article.builder()
                    .title(this.title)
                    .body(this.body)
                    .build();
        }
    }


    @Builder
    @Data
    public static class Response {
        private Long id;
        private String title;
        private String body;
    }

    @Builder
    @Data
    public static class edit {
        private Long id;
        private String title;
        private String body;
    }
}
