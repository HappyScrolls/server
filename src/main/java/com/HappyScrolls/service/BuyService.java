package com.HappyScrolls.service;


import com.HappyScrolls.dto.BuyDTO;
import com.HappyScrolls.entity.Buy;
import com.HappyScrolls.entity.Cart;
import com.HappyScrolls.entity.Member;
import com.HappyScrolls.entity.Product;
import com.HappyScrolls.exception.NoAuthorityExceoption;
import com.HappyScrolls.exception.PointLackException;
import com.HappyScrolls.repository.BuyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
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

    public List<Buy> buyCreate(Member member, BuyDTO.RequestCart request) {

        List<Buy> response = new ArrayList<>();

        Integer requirePoints=0;
        List<Cart> cartList = new ArrayList<>();
        for (Long cartId : request.getCart()) {
            Cart cart=cartService.cartFind(cartId);
            requirePoints += cart.getProduct().getPrice();
            cartList.add(cart);
        }
        if (requirePoints > member.getPoint()) {
            throw new PointLackException(String.format("포인트가 부족합니다 보유 포인트 :[%s] 필요 포인트 : [%s] 부족한 포인트 : [%s]",  member.getPoint(),requirePoints,requirePoints- member.getPoint()));
        }

//        memberService.decreasePoint(member,requirePoints);

        for (Cart cart : cartList) {
            Buy buy = new Buy();
            buy.setCreateDate(LocalDateTime.now());
            buy.setMember(member);
            buy.setProduct(cart.getProduct());
            buyRepository.save(buy);
            response.add(buy);
        }
        System.out.println(member);
        System.out.println(requirePoints);
        System.out.println(cartList);
        applicationEventPublisher.publishEvent(new BuyEvent(member,requirePoints,cartList));//어떻게테스트??

//        for (Cart cart : cartList) {
//            cartService.cartDelete(cart);
//        }

        return response;
    }

    public List<Buy> buyRetrieveUser(Member member) {
        List<Buy> buyList = buyRepository.findAllByMember(member);

        return buyList;
    }



}
