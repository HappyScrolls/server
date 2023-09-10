package com.HappyScrolls.dto;

import com.HappyScrolls.entity.Article;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
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
                    .createDate(LocalDate.now())
                    .viewCount(0)
                    .build();
        }

    }


    @Builder
    @Getter
    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class DetailResponse {
        private Long id;
        private String title;
        private String body;
        private LocalDate createDate;
        private List<TagDTO.Response> tags;



    }
    @Builder
    @Getter
    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class ListResponse {

        @Schema(description = "id", example = "1", required = true)
        private Long id;

        @Schema(description = "제목", example = "제목입니다.", required = true)
        private String title;

        @Schema(description = "작성자", example = "최혁순", required = true)
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

    @Builder
    @Getter
    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class Test {
        private Long id;
        private String title;
        private String body;
        private Long memberId;
        private String nickname;

    }
}
