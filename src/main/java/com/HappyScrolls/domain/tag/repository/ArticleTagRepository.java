package com.HappyScrolls.domain.tag.repository;

import com.HappyScrolls.domain.article.entity.Article;
import com.HappyScrolls.domain.tag.entity.ArticleTag;
import com.HappyScrolls.domain.tag.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleTagRepository extends JpaRepository<ArticleTag, Long> ,ArticleTagCustomRepository{

    List<ArticleTag> findByArticle(Article article);

    List<ArticleTag> findAllByTag(Tag findTag);

    List<ArticleTag> findAllByTagIn(List<Tag> tags);


}
