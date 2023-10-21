package com.HappyScrolls.domain.cart.repository;

import com.HappyScrolls.domain.cart.entity.Cart;
import com.HappyScrolls.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findAllByMember(Member member);
}
