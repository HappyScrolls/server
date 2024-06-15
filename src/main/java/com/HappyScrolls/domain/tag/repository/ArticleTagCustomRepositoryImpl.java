package com.HappyScrolls.domain.tag.repository;

import com.HappyScrolls.domain.tag.entity.ArticleTag;
import com.HappyScrolls.domain.tag.entity.Tag;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.HappyScrolls.domain.article.entity.QArticle.article;
import static com.HappyScrolls.domain.tag.entity.QArticleTag.articleTag;
@Repository
public class ArticleTagCustomRepositoryImpl implements ArticleTagCustomRepository {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;


    @Override
    public List<ArticleTag> findByTagPaging(Long lastindex, Tag tag) {
        jpaQueryFactory
                .select(articleTag)
                .from(articleTag)
                .innerJoin(articleTag.article, article)
                .fetchJoin()
                .where(article.id.gt(lastindex))
                .limit(100)
                .fetch();
        return jpaQueryFactory
                .select(articleTag)
                .from(articleTag)
                .innerJoin(articleTag.article, article)
                .fetchJoin()
                .where(article.id.gt(lastindex))
                .limit(100)
                .fetch();
    }
}
