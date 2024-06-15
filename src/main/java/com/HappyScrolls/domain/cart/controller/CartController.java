package com.HappyScrolls.domain.cart.controller;


import com.HappyScrolls.domain.cart.dto.CartDTO;
import com.HappyScrolls.domain.member.entity.Member;
import com.HappyScrolls.domain.cart.service.CartService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @ApiOperation(value = "장바구니 생성")
    @PostMapping("")
    public ResponseEntity<Long> createCart(@AuthenticationPrincipal Member member, @RequestBody CartDTO.Request request) {
        return ResponseEntity.ok(cartService.cartCreate(member,request));
    }

    @ApiOperation(value = "사용자 장바구니 조회")
    @GetMapping("")
    public ResponseEntity<List<CartDTO.Response>> retrieveUserCart(@AuthenticationPrincipal Member member) {
        return ResponseEntity.ok( cartService.userCartRetrieve(member));
    }




}
