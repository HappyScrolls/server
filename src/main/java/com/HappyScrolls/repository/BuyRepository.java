package com.HappyScrolls.repository;

import com.HappyScrolls.entity.Buy;
import com.HappyScrolls.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BuyRepository extends JpaRepository<Buy, Long> {

    List<Buy> findAllByMember(Member member);
}
