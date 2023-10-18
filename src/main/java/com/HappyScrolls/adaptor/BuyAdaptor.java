package com.HappyScrolls.adaptor;

import com.HappyScrolls.dto.BuyDTO;
import com.HappyScrolls.entity.Buy;
import com.HappyScrolls.entity.BuyEvent;
import com.HappyScrolls.entity.Cart;
import com.HappyScrolls.entity.Member;
import com.HappyScrolls.exception.PointLackException;
import com.HappyScrolls.repository.BuyRepository;
import com.HappyScrolls.service.CartService;
import com.HappyScrolls.service.MemberService;
import com.HappyScrolls.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
