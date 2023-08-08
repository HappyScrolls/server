package com.HappyScrolls.dto;

import com.HappyScrolls.entity.Comment;
import lombok.Builder;
import lombok.Data;

public class CommentDTO {



    @Builder
    @Data
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
    @Data
    public static class Response {
        private Long id;
        private String body;
    }

    @Builder
    @Data
    public static class Edit {
        private Long id;
        private String body;
    }
}
