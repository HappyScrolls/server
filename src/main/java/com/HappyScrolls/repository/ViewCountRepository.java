package com.HappyScrolls.repository;

import com.HappyScrolls.entity.ViewCount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViewCountRepository extends JpaRepository<ViewCount,Long> {
}
