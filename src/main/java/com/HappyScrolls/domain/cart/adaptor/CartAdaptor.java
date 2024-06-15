package com.HappyScrolls.domain.cart.adaptor;

import com.HappyScrolls.config.Adaptor;
import com.HappyScrolls.domain.cart.entity.Cart;
import com.HappyScrolls.domain.member.entity.Member;
import com.HappyScrolls.domain.cart.repository.CartRepository;
import com.HappyScrolls.domain.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.NoSuchElementException;

@Adaptor
public class CartAdaptor {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductService productService;

    public Long cartCreate(Cart cart) {

        return cartRepository.save(cart).getId();
    }

    public List<Cart> userCartRetrieve(Member member) {
        List<Cart> userCarts = cartRepository.findAllByMember(member);

        return userCarts;
    }

    public Cart cartFind(Long id) {
        Cart cart = cartRepository.findById(id).orElseThrow(()-> new NoSuchElementException(String.format("cart[%s] 장바구니 항목을 찾을 수 없습니다", id))); //%s?
        return cart;
    }
}
