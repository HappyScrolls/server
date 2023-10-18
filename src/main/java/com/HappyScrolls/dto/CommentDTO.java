package com.HappyScrolls.dto;

import com.HappyScrolls.entity.Comment;
import lombok.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CommentDTO {



    @Builder
    @Getter
    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class ChildRequest {
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
    public static class ParentResponse {
        private Long id;
        private String body;
        public static CommentDTO.ParentResponse toParentResponseDto(Comment comment) {
            return CommentDTO.ParentResponse
                    .builder()
                    .id(comment.getId())
                    .body(comment.getBody())
                    .build();
        }

        public static CommentDTO.ChildResponse toChildResponseDto(Comment comment) {
            return CommentDTO.ChildResponse
                    .builder()
                    .id(comment.getId())
                    .parentId(comment.getParentId())
                    .body(comment.getBody())
                    .build();
        }



    }

    @Builder
    @Getter
    @RequiredArgsConstructor
    @AllArgsConstructor
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


    @Builder
    @Getter
    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long id;
        private String body;
        private Boolean isParent;
        private Long parentId;

        public static List<Response> toCommentResponseDtoList(List<Comment> comments) {
            return comments.stream()
                    .map(comment -> CommentDTO.Response
                            .builder()
                            .id(comment.getId())
                            .body(comment.getBody())
                            .isParent(comment.getIsParent())
                            .parentId(comment.getParentId())
                            .build())
                    .collect(Collectors.toList());
        }
    }

}
