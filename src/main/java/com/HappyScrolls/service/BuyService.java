package com.HappyScrolls.service;


import com.HappyScrolls.dto.BuyDTO;
import com.HappyScrolls.entity.Buy;
import com.HappyScrolls.entity.Cart;
import com.HappyScrolls.entity.Member;
import com.HappyScrolls.entity.Product;
import com.HappyScrolls.repository.BuyRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<BuyDTO.Response> buyCreate(Member member, BuyDTO.RequestCart request) {

        List<BuyDTO.Response> response = new ArrayList<>();

        for (Long cartId : request.getCart()) {

            Cart cart = cartService.cartFind(cartId);

            Buy buy = new Buy();
            buy.setCreateDate(LocalDateTime.now());
            buy.setMember(member);
            buy.setProduct(cart.getProduct());
            buyRepository.save(buy);
            response.add(BuyDTO.Response.builder()
                    .id(buy.getId())
                    .build());
        }

        return response;
    }

    public List<BuyDTO.Response> buyRetrieveUser(Member member) {
        List<Buy> buyList = buyRepository.findAllByMember(member);

        return buyList.stream()
                .map(buy -> BuyDTO.Response
                        .builder()
                        .id(buy.getId())
                        .productId(buy.getProduct().getId())
                        .createTime(buy.getCreateDate())
                        .build())
                .collect(Collectors.toList());
    }
}
