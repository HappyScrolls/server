package com.HappyScrolls.adaptor;

import com.HappyScrolls.domain.cart.adaptor.CartAdaptor;
import com.HappyScrolls.domain.cart.entity.Cart;
import com.HappyScrolls.domain.member.entity.Member;
import com.HappyScrolls.domain.cart.repository.CartRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import java.util.List;
@ExtendWith(MockitoExtension.class)
public class CartAdaptorTest {

    @InjectMocks
    private CartAdaptor cartAdaptor;

    @Mock
    private CartRepository cartRepository;


    @Test
    @DisplayName("장바구니 생성 테스트")
    public void 장바구니_생성_테스트() {
        Cart mockCart = Cart.builder().id(1l).build();

        when(cartRepository.save(any())).thenReturn(mockCart);

        Long resultId = cartAdaptor.cartCreate(new Cart());
        assertEquals(1L, resultId);
    }

    @Test
    @DisplayName("사용자 장바구니 검색 테스트")
    public void 사용자_장바구니_검색_테스트() {
        Member mockMember = new Member();
        Cart mockCart1 = new Cart();
        Cart mockCart2 = new Cart();

        when(cartRepository.findAllByMember(any(Member.class))).thenReturn(Arrays.asList(mockCart1, mockCart2));

        List<Cart> resultCarts = cartAdaptor.userCartRetrieve(mockMember);
        assertEquals(2, resultCarts.size());
    }

    @Test
    @DisplayName("장바구니 찾기 테스트")
    public void 장바구니_찾기_테스트() {
        Cart mockCart = Cart.builder().id(1l).build();


        when(cartRepository.findById(any(Long.class))).thenReturn(Optional.of(mockCart));

        Cart resultCart = cartAdaptor.cartFind(1L);
        assertEquals(1L, resultCart.getId());
    }

    @Test
    @DisplayName("장바구니 찾기 실패 테스트")
    public void 장바구니_찾기_실패_테스트() {
        when(cartRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> cartAdaptor.cartFind(1L));
    }
}
