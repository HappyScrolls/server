package com.HappyScrolls.controller;


import com.HappyScrolls.dto.ArticleDTO;
import com.HappyScrolls.dto.CartDTO;
import com.HappyScrolls.entity.Cart;
import com.HappyScrolls.entity.Member;
import com.HappyScrolls.service.CartService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @ApiOperation(value = "장바구니 생성")
    @PostMapping("")
    public ResponseEntity createCart(@AuthenticationPrincipal Member member, @RequestBody CartDTO.Request request) {

        Long response = cartService.cartCreate(member,request);

        return new ResponseEntity(response, HttpStatus.CREATED);
    }

    @ApiOperation(value = "사용자 장바구니 조회")
    @GetMapping("")
    public ResponseEntity retrieveUserCart(@AuthenticationPrincipal Member member) {
        List<Cart>  response = cartService.userCartRetrieve(member);
        return new ResponseEntity(toResponseDtoList(response), HttpStatus.ACCEPTED);
    }
    public static CartDTO.Response toResponseDto(Cart cart) {
        return CartDTO.Response.builder()
                        .id(cart.getId())
                        .build();
    }
    public static List<CartDTO.Response> toResponseDtoList(List<Cart> carts) {
        return carts.stream()
                .map(cart -> CartDTO.Response.builder()
                        .id(cart.getId())
                        .build())
                .collect(Collectors.toList());
    }



}
