package com.HappyScrolls.domain.cart.dto;

import com.HappyScrolls.domain.cart.entity.Cart;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

public class CartDTO {


    @Builder
    @Getter
    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class Request{

        private Long productId;
    }


    @Builder
    @Getter
    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long id;

        public static CartDTO.Response toResponseDto(Cart cart) {
            return CartDTO.Response.builder()
                    .id(cart.getId())
                    .build();
        }
        public static List<Response> toResponseDtoList(List<Cart> carts) {
            return carts.stream()
                    .map(cart -> CartDTO.Response.builder()
                            .id(cart.getId())
                            .build())
                    .collect(Collectors.toList());
        }
    }


}
