package com.HappyScrolls.dto;

import com.HappyScrolls.entity.Article;
import lombok.Builder;
import lombok.Data;
import org.apache.coyote.Request;

public class ArticleDTO {


    @Builder
    @Data
    public static class Request{
        private String title;
        private String body;

        public Article toEntity() {
            return Article.builder()
                    .title(this.title)
                    .Body(this.body)
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
}
