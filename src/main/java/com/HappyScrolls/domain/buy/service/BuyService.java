package com.HappyScrolls.domain.buy.service;


import com.HappyScrolls.domain.buy.adaptor.BuyAdaptor;
import com.HappyScrolls.domain.buy.entity.Buy;
import com.HappyScrolls.domain.cart.adaptor.CartAdaptor;
import com.HappyScrolls.domain.cart.entity.Cart;
import com.HappyScrolls.domain.event.BuyEvent;
import com.HappyScrolls.domain.member.entity.Member;
import com.HappyScrolls.domain.buy.dto.BuyDTO;
import com.HappyScrolls.exception.PointLackException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BuyService {

    @Autowired
    private CartAdaptor cartAdaptor;

    @Autowired
    private BuyAdaptor buyAdaptor;
    @Autowired
    private  ApplicationEventPublisher applicationEventPublisher;


    public List<Long> buyCreate(Member member, BuyDTO.RequestCart request) {

        List<Buy> response = new ArrayList<>();

        Integer requirePoints=0;
        List<Cart> cartList = new ArrayList<>();
        for (Long cartId : request.getCart()) {
            Cart cart=cartAdaptor.cartFind(cartId);
            requirePoints += cart.getProduct().getPrice();
            if (cart.getProduct().getQuantity() <= 0) {
                throw new PointLackException(String.format(" 재고수량이 부족합니다 [%s]", cart.getProduct().getId()));
            }
            cartList.add(cart);
        }
        if (requirePoints > member.getPoint()) {
            throw new PointLackException(String.format("포인트가 부족합니다 보유 포인트 :[%s] 필요 포인트 : [%s] 부족한 포인트 : [%s]",  member.getPoint(),requirePoints,requirePoints- member.getPoint()));
        }

        for (Cart cart : cartList) {
            cart.getProduct().decreaseQuantity();

            Buy buy = new Buy();
            buy.setCreateDate(LocalDateTime.now());
            buy.setMember(member);
            buy.setProduct(cart.getProduct());
            buyAdaptor.saveEntity(buy);
            response.add(buy);
        }



        applicationEventPublisher.publishEvent(new BuyEvent(member,requirePoints,cartList));//어떻게테스트??


        return response.stream().map(res-> res.getId())
                .collect(Collectors.toList());
    }

    public List<BuyDTO.Response> buyRetrieveUser(Member member) {
        return BuyDTO.Response.toResponseDtoList(buyAdaptor.buyRetrieveUser(member));

    }



}
