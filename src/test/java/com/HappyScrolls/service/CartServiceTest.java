package com.HappyScrolls.service;

import com.HappyScrolls.dto.ArticleDTO;
import com.HappyScrolls.dto.CartDTO;
import com.HappyScrolls.entity.Article;
import com.HappyScrolls.entity.Cart;
import com.HappyScrolls.entity.Member;
import com.HappyScrolls.entity.Product;
import com.HappyScrolls.repository.CartRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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
    private CartRepository cartRepository;


    @Mock
    private ProductService productService;

    @Test
    void 장바구니_생성_성공() {

        Member member = Member.builder().id(1l).email("chs98412@naver,com").nickname("hyuksoon").thumbnail("img").build();
        Product product = new Product(1l, "이름", "내용", 100);
        CartDTO.Request request = CartDTO.Request.builder().productId(1l).build();

        Cart cart = Cart.builder().product(product).member(member).build();

        when(cartRepository.save(any())).thenReturn(cart);
        when(productService.productRetrieve(any())).thenReturn(product);

        Long response =   cartService.cartCreate(member, request);

        verify(cartRepository).save(any());
    }

    @Test
    void 사용자_장바구니_조회_성공() {

        String testEmail = "chs98412@naver,com";
        Member member = Member.builder().id(1l).email(testEmail).nickname("hyuksoon").thumbnail("img").build();
        Product product1 = new Product(1l, "이름1", "내용1", 100);
        Product product2 = new Product(2l, "이름2", "내용2", 100);
        Cart cart1 = Cart.builder().product(product1).member(member).build();
        Cart cart2 = Cart.builder().product(product2).member(member).build();


        List<Cart> carts = new ArrayList<>();
        carts.add(cart1);
        carts.add(cart2);

        when(cartRepository.findAllByMember(any())).thenReturn(carts);


        List<Cart> response = cartService.userCartRetrieve(member);

        verify(cartRepository).findAllByMember(member);


            assertThat(response.get(0).getId()).isEqualTo(carts.get(0).getId());
            assertThat(response.get(0).getProduct()).isEqualTo(carts.get(0).getProduct());
        assertThat(response.get(0).getMember()).isEqualTo(carts.get(0).getMember());

        assertThat(response.get(1).getId()).isEqualTo(carts.get(1).getId());
        assertThat(response.get(1).getProduct()).isEqualTo(carts.get(1).getProduct());
        assertThat(response.get(1).getMember()).isEqualTo(carts.get(1).getMember());

    }

    @Test
    @DisplayName("장바구니 단건 조회 기능이 제대로 동작하는지 확인")
    void 장바구니_단건조회_성공_테스트() {

        Member member = Member.builder().id(1l).email("email1").nickname("hyuksoon").thumbnail("img").build();
        Product product1 = new Product(1l, "이름1", "내용1", 100);
        Cart cart= Cart.builder().id(1l).product(product1).member(member).build();


        when(cartRepository.findById(any())).thenReturn(Optional.of(cart));

        Cart response = cartService.cartFind(1L);
        verify(cartRepository).findById(1L);
        assertThat(response).isEqualTo(cart);
    }


    @Test
    @DisplayName("상품 단건 조회 기능이 조회를 할 수 없을 때 예외처리를 하는지 확인")
    void 상품_단건조회_예외_테스트() {
        Long testId = 1L;
        when(cartRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> cartService.cartFind(testId));


        verify(cartRepository).findById(testId);
    }

}