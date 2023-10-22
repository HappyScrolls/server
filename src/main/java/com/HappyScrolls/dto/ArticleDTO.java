package com.HappyScrolls.dto;

import com.HappyScrolls.entity.Article;
import com.HappyScrolls.entity.Sticker;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ArticleDTO {


    @Builder
    @Getter
    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class Request{
        private String title;
        private String body;

        private List<TagDTO.Request> tags;

        private Sticker sticker;

        public Article toEntity() {
            return Article.builder()
                    .title(this.title)
                    .body(this.body)
                    .createDate(LocalDate.now())
                    .viewCount(0)
                    .sticker(sticker)
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
        public static ArticleDTO. DetailResponse toResponseDto(Article article) {
            return ArticleDTO.DetailResponse.builder()
                    .id(article.getId())
                    .title(article.getTitle())
                    .body(article.getBody())
                    .build();
        }


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
        public static List<ArticleDTO.ListResponse> toResponseDtoList(List<Article> articles) {
            return articles.stream()
                    .map(article -> ArticleDTO.ListResponse.builder()
                            .id(article.getId())
                            .title(article.getTitle())
                            .member(article.getMember().getNickname())
                            .build())
                    .collect(Collectors.toList());
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
