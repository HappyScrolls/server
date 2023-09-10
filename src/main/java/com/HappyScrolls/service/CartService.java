package com.HappyScrolls.service;


import com.HappyScrolls.dto.ArticleDTO;
import com.HappyScrolls.dto.CartDTO;
import com.HappyScrolls.entity.Article;
import com.HappyScrolls.entity.Cart;
import com.HappyScrolls.entity.Member;
import com.HappyScrolls.entity.Product;
import com.HappyScrolls.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private ProductService productService;

    public Long cartCreate(Member member, CartDTO.Request request) {

        Product product = productService.productRetrieve(request.getProductId());
        Cart cart = Cart.builder()
                .product(product)
                .member(member)
                .build();
        cartRepository.save(cart);

        return cart.getId();
    }

    public List<Cart> userCartRetrieve(Member member) {
        List<Cart> userCarts = cartRepository.findAllByMember(member);

        return userCarts;
    }

    public Cart cartFind(Long id) {
        Cart cart = cartRepository.findById(id).orElseThrow(()-> new NoSuchElementException(String.format("cart[%s] 장바구니 항목을 찾을 수 없습니다", id))); //%s?
        return cart;
    }

//    public void cartDelete(Cart cart) {
//        cartRepository.delete(cart);
//    }

    @EventListener
    public void test(BuyEvent event) {
        for (Cart cart : event.getCartList()) {
            cartRepository.delete(cart);
        }
    }
}
