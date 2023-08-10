package com.HappyScrolls.dto;

import com.HappyScrolls.entity.Comment;
import lombok.*;

import java.util.Objects;

public class CommentDTO {



    @Builder
    @Getter
    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class Request{
        private Long postId;
        private String body;

        public Comment toEntity() {
            return Comment.builder()
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
        private String body;
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Response response = (Response) o;
            return Objects.equals(id, response.id) && Objects.equals(body, response.body);
        }



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
