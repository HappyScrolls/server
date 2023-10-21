package com.HappyScrolls.domain.buy.dto;

import com.HappyScrolls.domain.buy.entity.Buy;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

        public static List<BuyDTO.Response> toResponseDtoList(List<Buy> buyList) {
            return buyList.stream()
                    .map(buy -> BuyDTO.Response
                            .builder()
                            .id(buy.getId())
                            .productId(buy.getProduct().getId())
                            .createTime(buy.getCreateDate())
                            .build())
                    .collect(Collectors.toList());

        }
    }

}
