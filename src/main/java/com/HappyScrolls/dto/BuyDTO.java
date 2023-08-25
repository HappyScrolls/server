package com.HappyScrolls.dto;

import lombok.*;

import java.util.List;

public class BuyDTO {

    @Builder
    @Getter
    @RequiredArgsConstructor
    @AllArgsConstructor
    @Data
    public static class RequestCart{

        private List<Long> cart;
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