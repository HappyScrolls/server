package com.HappyScrolls.service;

import com.HappyScrolls.domain.cart.adaptor.CartAdaptor;
import com.HappyScrolls.domain.cart.service.CartService;
import com.HappyScrolls.domain.product.adaptor.ProductAdaptor;
import com.HappyScrolls.domain.cart.dto.CartDTO;
import com.HappyScrolls.domain.cart.entity.Cart;
import com.HappyScrolls.domain.member.entity.Member;
import com.HappyScrolls.domain.product.entity.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {
    @InjectMocks
    private CartService cartService;

    @Mock
    private ProductAdaptor productAdaptor;

    @Mock
    private CartAdaptor cartAdaptor;

    @Test
    @DisplayName("장바구니 생성 테스트")
    void 장바구니_생성_테스트() {
        Member member = new Member();
        Product product = new Product();
        CartDTO.Request request = CartDTO.Request.builder().productId(1L).build();

        when(productAdaptor.productRetrieve(any())).thenReturn(product);
        when(cartAdaptor.cartCreate(any())).thenReturn(1L);

        Long result = cartService.cartCreate(member, request);

        assertEquals(1L, result);
    }
    @Test
    @DisplayName("사용자 장바구니 조회 테스트")
    void 사용자_장바구니_조회_테스트() {
        Member member = new Member();
        Cart cart = Cart.builder().id(1l).build();

        List<Cart> carts = new ArrayList<>();
        carts.add(cart);

        when(cartAdaptor.userCartRetrieve(any())).thenReturn(carts);

        List<CartDTO.Response> result = cartService.userCartRetrieve(member);


        assertEquals(1, result.size());
        assertEquals(result.get(0).getId(),1l);
    }


    @Test
    @DisplayName("장바구니 찾기 테스트")
    void 장바구니_찾기_테스트() {
        Cart cart = Cart.builder().id(1l).build();

        when(cartAdaptor.cartFind(any())).thenReturn(cart);

        CartDTO.Response result = cartService.cartFind(1L);

        assertEquals(1L, result.getId());
    }

}