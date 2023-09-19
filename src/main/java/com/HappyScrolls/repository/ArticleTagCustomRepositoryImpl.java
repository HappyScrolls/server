package com.HappyScrolls.repository;

import com.HappyScrolls.entity.ArticleTag;
import com.HappyScrolls.entity.Tag;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.HappyScrolls.entity.QArticleTag.articleTag;
import static com.HappyScrolls.entity.QArticle.article;

@Repository
public class ArticleTagCustomRepositoryImpl implements ArticleTagCustomRepository {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;
//    @Override
//    public List<ArticleTag> findByTagPaging(Long lastindex, Tag tag) {
//        return jpaQueryFactory.selectFrom(articleTag)
//                .innerJoin(articleTag.article, article)
//                .fetchJoin()
//                .where(article.id.gt(lastindex))
//                .limit(100)
//                .fetch();
//    }

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
