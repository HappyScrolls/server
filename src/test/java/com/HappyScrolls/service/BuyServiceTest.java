package com.HappyScrolls.service;

import com.HappyScrolls.dto.BuyDTO;
import com.HappyScrolls.entity.Buy;
import com.HappyScrolls.entity.Cart;
import com.HappyScrolls.entity.Member;
import com.HappyScrolls.entity.Product;
import com.HappyScrolls.exception.PointLackException;
import com.HappyScrolls.repository.BuyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class BuyServiceTest {

    @InjectMocks
    private BuyService buyService;
    @Mock
    private BuyRepository buyRepository;

    @Mock
    private CartService cartService;

    @Mock
    private ProductService productService;
    @Mock
    private MemberService memberService;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;


    @Test
    void 구매_생성_성공() {
        String testEmail = "chs98412@naver,com";
        Member member = Member.builder().id(1l).email(testEmail).nickname("hyuksoon").thumbnail("img").point(120).build();
        Product product1 = new Product(1l, "이름1", "내용1", 100);
        Cart cart1 = Cart.builder().id(1l).product(product1).member(member).build();
        List<Long> carts = new ArrayList<>();
        carts.add(1l);
        BuyDTO.RequestCart request = new BuyDTO.RequestCart(carts);
        Buy buy = Buy.builder().product(product1).member(member).createDate(LocalDateTime.now()).build();

        when(cartService.cartFind(any())).thenReturn(cart1);

        when(buyRepository.save(any())).thenReturn(buy);
        List<Buy> response = buyService.buyCreate(member, request);

        assertThat(response.get(0).getMember()).isEqualTo(member);
        assertThat(response.get(0).getProduct()).isEqualTo(product1);
    }

    @Test
    void 구매_생성_실패() {
        String testEmail = "chs98412@naver,com";
        Member member = Member.builder().id(1l).email(testEmail).nickname("hyuksoon").thumbnail("img").point(100).build();
        Product product1 = new Product(1l, "이름1", "내용1", 120);
        Cart cart1 = Cart.builder().id(1l).product(product1).member(member).build();
        List<Long> carts = new ArrayList<>();
        carts.add(1l);
        BuyDTO.RequestCart request = new BuyDTO.RequestCart(carts);
        Buy buy = Buy.builder().product(product1).member(member).createDate(LocalDateTime.now()).build();

        when(cartService.cartFind(any())).thenReturn(cart1);
        assertThrows(PointLackException.class, () -> buyService.buyCreate(member,request));

    }

}