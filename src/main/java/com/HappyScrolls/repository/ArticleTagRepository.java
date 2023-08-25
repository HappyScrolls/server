package com.HappyScrolls.repository;

import com.HappyScrolls.entity.Article;
import com.HappyScrolls.entity.ArticleTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleTagRepository extends JpaRepository<ArticleTag, Long> {

    List<ArticleTag> findByArticle(Article article);
}
