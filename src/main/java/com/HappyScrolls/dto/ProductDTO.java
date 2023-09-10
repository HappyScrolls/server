package com.HappyScrolls.dto;

import com.HappyScrolls.entity.Product;
import lombok.*;

public class ProductDTO {



    @Builder
    @Getter
    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class Request{

        private String name;
        private String description;
        private Integer price;

        public Product toEntity() {
            return Product.builder()
                    .name(name)
                    .description(description)
                    .price(price)
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

        private String name;
        private String description;
        private Integer price;


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
