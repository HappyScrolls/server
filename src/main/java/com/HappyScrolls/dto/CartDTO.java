package com.HappyScrolls.dto;

import com.HappyScrolls.entity.Product;
import lombok.*;

public class CartDTO {


    @Builder
    @Getter
    @RequiredArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Request{

        private Long productId;
    }


    @Builder
    @Getter
    @RequiredArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Response {
        private Long id;
    }


}
