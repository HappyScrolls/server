package com.HappyScrolls.dto;

import com.HappyScrolls.entity.Article;
import lombok.*;

import java.util.Objects;

public class ArticleDTO {


    @Builder
    @Getter
    @RequiredArgsConstructor
    @AllArgsConstructor
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
    @Getter
    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private String title;
        private String body;


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Response response = (Response) o;
            return Objects.equals(id, response.id) && Objects.equals(title, response.title) && Objects.equals(body, response.body);
        }

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
