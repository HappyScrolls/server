package com.HappyScrolls.domain.member.repository;

import com.HappyScrolls.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends Repository<Member,Long> {
    Optional<Member> findByEmail(String email);

    @Query(value = "SELECT * FROM Member  limit 10", nativeQuery = true)
    List<Member> findPage();

    Member save(Member entity);
}
