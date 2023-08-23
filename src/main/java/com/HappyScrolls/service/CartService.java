package com.HappyScrolls.service;


import com.HappyScrolls.dto.CartDTO;
import com.HappyScrolls.entity.Cart;
import com.HappyScrolls.entity.Member;
import com.HappyScrolls.entity.Product;
import com.HappyScrolls.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private ProductService productService;

    public CartDTO.Response cartCreate(Member member, CartDTO.Request request) {

        Product product = productService.productFind(request.getProductId());
        Cart cart = Cart.builder()
                .product(product)
                .member(member)
                .build();
        cartRepository.save(cart);

        return CartDTO.Response.builder()
                .id(cart.getId())
                .build();
    }
}
