package com.HappyScrolls.repository;

import com.HappyScrolls.dto.testRepo;
import com.HappyScrolls.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {
    Optional<Member> findByEmail(String email);

    @Query(value = "SELECT * FROM Member  limit 10", nativeQuery = true)
    List<Member> findPage();
}
