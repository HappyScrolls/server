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
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public List<Long> buyCreate(Member member, BuyDTO.RequestCart request) {

        List<Buy> response = new ArrayList<>();

        Integer requirePoints=0;
        List<Cart> cartList = new ArrayList<>();
        for (Long cartId : request.getCart()) {

            Cart cart=cartAdaptor.cartFind(cartId);
            requirePoints += cart.getProduct().getPrice();
            cart.getProduct().decreaseQuantity();
            Buy buy = Buy.builder().createDate(LocalDateTime.now()).member(member).product(cart.getProduct()).build();
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
