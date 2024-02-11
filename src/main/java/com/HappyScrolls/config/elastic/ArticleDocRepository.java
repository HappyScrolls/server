package com.HappyScrolls.config.elastic;

import com.HappyScrolls.domain.article.entity.Article;
import com.HappyScrolls.domain.article.repository.ArticleCustomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ArticleDocRepository extends ElasticsearchRepository<ArticleDoc, Long>, CrudRepository<ArticleDoc, Long>, ArticleDocCustomRepository {
    List<ArticleDoc> findAllByTitle(String title);

    List<ArticleDoc> findAllByTitleContaining(String title);

    Page<ArticleDoc> findAllByTitleContaining(String title,Pageable pageable);
}
