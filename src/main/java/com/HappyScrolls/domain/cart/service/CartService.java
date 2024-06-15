package com.HappyScrolls.domain.cart.service;


import com.HappyScrolls.domain.cart.adaptor.CartAdaptor;
import com.HappyScrolls.domain.cart.entity.Cart;
import com.HappyScrolls.domain.event.BuyEvent;
import com.HappyScrolls.domain.member.entity.Member;
import com.HappyScrolls.domain.product.adaptor.ProductAdaptor;
import com.HappyScrolls.domain.product.entity.Product;
import com.HappyScrolls.domain.cart.dto.CartDTO;
import com.HappyScrolls.domain.cart.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductAdaptor productAdaptor;

    @Autowired
    private CartAdaptor cartAdaptor;

    public Long cartCreate(Member member, CartDTO.Request request) {

        Product product = productAdaptor.productRetrieve(request.getProductId());
        Cart cart = Cart.builder()
                .product(product)
                .member(member)
                .build();


        return cartAdaptor.cartCreate(cart);
    }

    public List<CartDTO.Response> userCartRetrieve(Member member) {
        return CartDTO.Response.toResponseDtoList(cartAdaptor.userCartRetrieve(member));
    }

    public CartDTO.Response cartFind(Long id) {
        return CartDTO.Response.toResponseDto(cartAdaptor.cartFind(id));
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
