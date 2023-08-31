package com.HappyScrolls.repository;

import com.HappyScrolls.entity.Article;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.HappyScrolls.entity.QArticle.article;
import static com.HappyScrolls.entity.QMember.member;

@Repository
public class ArticleCustomRepositoryImpl implements ArticleCustomRepository{

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Article> zeroOffsetPaging(Long startIdx,Integer limitPage) {
        return jpaQueryFactory.selectFrom(article)
                .innerJoin(article.member, member)
                .fetchJoin()
                .where(article.id.gt(startIdx))
                .limit(limitPage)
                .fetch();
    }


    @Override
    public List<Article> coveringPaging(Integer page, Integer limit) {
        List<Long> ids = jpaQueryFactory
                .select(article.id)
                .from(article)
                .limit(limit)
                .offset(page * limit)
                .fetch();

        return jpaQueryFactory
                .selectFrom(article)
                .innerJoin(article.member, member)
                .fetchJoin()
                .where(article.id.in(ids))
                .fetch();
    }
}
