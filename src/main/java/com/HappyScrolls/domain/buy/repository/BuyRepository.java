package com.HappyScrolls.domain.buy.repository;

import com.HappyScrolls.domain.buy.entity.Buy;
import com.HappyScrolls.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BuyRepository extends JpaRepository<Buy, Long> {

    List<Buy> findAllByMember(Member member);
}
