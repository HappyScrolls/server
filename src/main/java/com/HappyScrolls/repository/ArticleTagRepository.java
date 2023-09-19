package com.HappyScrolls.repository;

import com.HappyScrolls.entity.Article;
import com.HappyScrolls.entity.ArticleTag;
import com.HappyScrolls.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleTagRepository extends JpaRepository<ArticleTag, Long> ,ArticleTagCustomRepository{

    List<ArticleTag> findByArticle(Article article);

    List<ArticleTag> findAllByTag(Tag findTag);

    List<ArticleTag> findAllByTagIn(List<Tag> tags);


}
