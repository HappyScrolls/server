package com.HappyScrolls.domain.product.dto;

import com.HappyScrolls.domain.product.entity.Product;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

public class ProductDTO {



    @Builder
    @Getter
    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class Request{

        private String name;
        private String description;
        private Integer price;

        private Integer quantity;

        public Product toEntity() {
            return Product.builder()
                    .name(name)
                    .description(description)
                    .price(price)
                    .quantity(quantity)
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

        public static ProductDTO.Response toResponseDto(Product product) {
            return ProductDTO.Response.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .description(product.getDescription())
                    .price(product.getPrice())
                    .build();
        }

        public static List<Response> toResponseDtoList(List<Product> products) {
            return products.stream()
                    .map(product -> ProductDTO.Response
                            .builder()
                            .id(product.getId())
                            .body(product.getName())
                            .description(product.getDescription())
                            .price(product.getPrice())
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
        private String name;
        private String description;
        private Integer expiration;

    }
}
