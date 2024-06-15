package com.HappyScrolls.domain.article.repository;

import com.HappyScrolls.domain.article.entity.Article;
import com.HappyScrolls.domain.event.ViewCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import java.time.LocalDate;
import java.util.Optional;

public interface ViewCountRepository extends Repository<ViewCount,Long> {

    Optional<ViewCount> findByCreateDateAndArticle(LocalDate today, Article article);

    Long countByCreateDateAndArticle(LocalDate date, Article article);

    ViewCount  save(ViewCount entity);

}
