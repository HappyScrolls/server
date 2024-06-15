package com.HappyScrolls.domain.event;

import com.HappyScrolls.domain.cart.entity.Cart;
import com.HappyScrolls.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class BuyEvent {
    private Member member;
    private Integer price;
    private List<Cart> cartList;

}
