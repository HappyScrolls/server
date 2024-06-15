package com.HappyScrolls.domain.cart.repository;

import com.HappyScrolls.domain.article.entity.Article;
import com.HappyScrolls.domain.cart.entity.Cart;
import com.HappyScrolls.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends Repository<Cart, Long> {
    List<Cart> findAllByMember(Member member);

    Cart save(Cart entity);

    Optional<Cart> findById(Long id);

    void delete(Cart entity);
}
