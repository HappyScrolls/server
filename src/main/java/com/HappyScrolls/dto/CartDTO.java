package com.HappyScrolls.dto;

import com.HappyScrolls.entity.Cart;
import com.HappyScrolls.entity.Product;
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
