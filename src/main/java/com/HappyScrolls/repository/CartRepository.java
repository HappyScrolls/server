package com.HappyScrolls.repository;

import com.HappyScrolls.entity.Cart;
import com.HappyScrolls.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findAllByMember(Member member);
}
