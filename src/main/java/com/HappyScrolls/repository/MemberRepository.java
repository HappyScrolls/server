package com.HappyScrolls.repository;

import com.HappyScrolls.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {
}
