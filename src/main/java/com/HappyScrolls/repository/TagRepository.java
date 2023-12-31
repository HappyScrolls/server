package com.HappyScrolls.repository;

import com.HappyScrolls.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findByBody(String body);

    List<Tag> findByBodyIn(List<String> tags);

    Long countByBody(String body);
}
