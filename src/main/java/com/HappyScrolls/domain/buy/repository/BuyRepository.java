package com.HappyScrolls.domain.buy.repository;

import com.HappyScrolls.domain.buy.entity.Buy;
import com.HappyScrolls.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface BuyRepository extends Repository<Buy, Long> {

    List<Buy> findAllByMember(Member member);

    Buy save(Buy entity);
}

