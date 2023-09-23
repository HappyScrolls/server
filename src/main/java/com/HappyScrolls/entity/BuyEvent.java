package com.HappyScrolls.service;

import com.HappyScrolls.entity.Cart;
import com.HappyScrolls.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
public class BuyEvent {
    private Member member;
    private Integer price;
    private List<Cart> cartList;

}
