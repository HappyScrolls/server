package com.HappyScrolls.service;


import com.HappyScrolls.dto.BuyDTO;
import com.HappyScrolls.entity.*;
import com.HappyScrolls.exception.PointLackException;
import com.HappyScrolls.repository.BuyRepository;
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
    private BuyRepository buyRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private  ApplicationEventPublisher applicationEventPublisher;

    public List<Long> buyCreate(Member member, BuyDTO.RequestCart request) {

        List<Buy> response = new ArrayList<>();

        Integer requirePoints=0;
        List<Cart> cartList = new ArrayList<>();
        for (Long cartId : request.getCart()) {
            Cart cart=cartService.cartFind(cartId);
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
            Buy buy = new Buy();
            buy.setCreateDate(LocalDateTime.now());
            buy.setMember(member);
            buy.setProduct(cart.getProduct());
            buyRepository.save(buy);
            response.add(buy);
        }

        applicationEventPublisher.publishEvent(new BuyEvent(member,requirePoints,cartList));//어떻게테스트??


        return response.stream().map(res-> res.getId())
                .collect(Collectors.toList());
    }

    public List<Buy> buyRetrieveUser(Member member) {
        List<Buy> buyList = buyRepository.findAllByMember(member);

        return buyList;
    }



}
