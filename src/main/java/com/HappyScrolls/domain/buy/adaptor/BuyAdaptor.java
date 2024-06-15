package com.HappyScrolls.domain.buy.adaptor;

import com.HappyScrolls.config.Adaptor;
import com.HappyScrolls.domain.buy.entity.Buy;
import com.HappyScrolls.domain.member.entity.Member;
import com.HappyScrolls.domain.buy.repository.BuyRepository;
import com.HappyScrolls.domain.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;

@Adaptor
public class BuyAdaptor {

    @Autowired
    private BuyRepository buyRepository;

    @Autowired
    private CartService cartService;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;



    public Long saveEntity(Buy buy) {
        return buyRepository.save(buy).getId();
    }

    public List<Buy> buyRetrieveUser(Member member) {
        List<Buy> buyList = buyRepository.findAllByMember(member);

        return buyList;
    }


}
