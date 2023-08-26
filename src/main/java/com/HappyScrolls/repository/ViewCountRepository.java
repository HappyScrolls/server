package com.HappyScrolls.repository;

import com.HappyScrolls.entity.ViewCount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface ViewCountRepository extends JpaRepository<ViewCount,Long> {
    Optional<ViewCount> findByCreateDate(LocalDate today);
}
