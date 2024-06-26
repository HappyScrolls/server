package com.HappyScrolls.service;

import com.HappyScrolls.domain.buy.adaptor.BuyAdaptor;
import com.HappyScrolls.domain.buy.entity.Buy;
import com.HappyScrolls.domain.buy.service.BuyService;
import com.HappyScrolls.domain.cart.adaptor.CartAdaptor;
import com.HappyScrolls.domain.member.entity.Member;
import com.HappyScrolls.domain.product.entity.Product;
import com.HappyScrolls.domain.buy.dto.BuyDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
public class BuyServiceTest {

    @InjectMocks
    private BuyService buyService;

    @Mock
    private CartAdaptor cartAdaptor;

    @Mock
    private BuyAdaptor buyAdaptor;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @Test
    @DisplayName("구매 내역 조회 테스트")
    void 구매_내역_조회_테스트() {
        Member member = new Member();

        Product product = Product.builder().id(1l).build();

        Buy buy = Buy.builder()
                .id(1L)
                .product(product)
                .member(member)
                .createDate(LocalDateTime.now())
                .build();

        List<Buy> buys = new ArrayList<>();
        buys.add(buy);

        when(buyAdaptor.buyRetrieveUser(any(Member.class))).thenReturn(buys);

        List<BuyDTO.Response> result = buyService.buyRetrieveUser(member);


        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(1L, result.get(0).getProductId());
    }

}
