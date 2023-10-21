package com.HappyScrolls.domain.tag.repository;

import com.HappyScrolls.domain.article.entity.Article;
import com.HappyScrolls.domain.tag.entity.ArticleTag;
import com.HappyScrolls.domain.tag.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface ArticleTagRepository extends Repository<ArticleTag, Long>,ArticleTagCustomRepository{

    List<ArticleTag> findByArticle(Article article);

    List<ArticleTag> findAllByTag(Tag findTag);

    List<ArticleTag> findAllByTagIn(List<Tag> tags);

    ArticleTag save(ArticleTag entity);


}
