package com.HappyScrolls.dto;

import com.HappyScrolls.entity.Comment;
import com.HappyScrolls.entity.Product;
import lombok.*;

import java.util.Objects;

public class ProductDTO {



    @Builder
    @Getter
    @RequiredArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Request{

        private String name;
        private String description;
        private Integer expiration;

        public Product toEntity() {
            return Product.builder()
                    .name(name)
                    .description(description)
                    .expiration(expiration)
                    .build();
        }
    }


    @Builder
    @Getter
    @RequiredArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Response {
        private Long id;
        private String body;

        private String name;
        private String description;
        private Integer expiration;


    }

    @Builder
    @Getter
    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class Edit {
        private Long id;
        private String name;
        private String description;
        private Integer expiration;
    }
}
