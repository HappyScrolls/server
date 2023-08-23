package com.HappyScrolls.controller;


import com.HappyScrolls.dto.ArticleDTO;
import com.HappyScrolls.dto.CartDTO;
import com.HappyScrolls.entity.Member;
import com.HappyScrolls.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("")
    public ResponseEntity createCart(@AuthenticationPrincipal Member member, @RequestBody CartDTO.Request request) {

        CartDTO.Response response = cartService.cartCreate(member,request);

        return new ResponseEntity(response, HttpStatus.CREATED);
    }



}
