package com.HappyScrolls.adaptor;

import com.HappyScrolls.dto.CartDTO;
import com.HappyScrolls.entity.Cart;
import com.HappyScrolls.entity.Member;
import com.HappyScrolls.entity.Product;
import com.HappyScrolls.repository.CartRepository;
import com.HappyScrolls.service.MemberService;
import com.HappyScrolls.service.ProductService;
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
