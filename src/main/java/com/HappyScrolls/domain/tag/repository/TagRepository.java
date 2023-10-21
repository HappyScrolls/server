package com.HappyScrolls.domain.tag.repository;

import com.HappyScrolls.domain.tag.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends Repository<Tag, Long> {

    Optional<Tag> findByBody(String body);

    List<Tag> findByBodyIn(List<String> tags);

    Long countByBody(String body);

    Tag save(Tag entity);
}
