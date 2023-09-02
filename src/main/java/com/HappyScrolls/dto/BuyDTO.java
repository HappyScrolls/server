package com.HappyScrolls.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public class BuyDTO {

    @Builder
    @Getter
    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class RequestCart{

        private List<Long> cart;
    }


    @Builder
    @Getter
    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long id;

        private Long productId;

        private LocalDateTime createTime;
    }

}
