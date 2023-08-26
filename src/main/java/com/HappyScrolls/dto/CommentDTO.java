package com.HappyScrolls.dto;

import com.HappyScrolls.entity.Comment;
import lombok.*;

import java.util.Objects;

public class CommentDTO {



    @Builder
    @Getter
    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class ChildRequest {
        private Long postId;
        private String body;

        private Boolean isParent;

        private Long parentId;

        public Comment toEntity() {
            return Comment.builder()
                    .body(this.body)
                    .isParent(this.isParent)
                    .parentId(this.parentId)
                    .build();
        }
    }
    @Builder
    @Getter
    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class ParentRequest {
        private Long postId;
        private String body;

        private Boolean isParent;

        public Comment toEntity() {
            return Comment.builder()
                    .body(this.body)
                    .isParent(this.isParent)
                    .parentId(null)
                    .build();
        }
    }

    @Builder
    @Getter
    @RequiredArgsConstructor
    @AllArgsConstructor
    @Data
    public static class ParentResponse {
        private Long id;
        private String body;

    }

    @Builder
    @Getter
    @RequiredArgsConstructor
    @AllArgsConstructor
    @Data
    public static class ChildResponse {
        private Long id;
        private String body;

        private Long parentId;

    }
    @Builder
    @Getter
    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class Edit {
        private Long id;
        private String body;
    }
}
