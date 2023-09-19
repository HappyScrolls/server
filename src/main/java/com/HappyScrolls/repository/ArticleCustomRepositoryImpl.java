package com.HappyScrolls.repository;

import com.HappyScrolls.entity.Article;
import com.HappyScrolls.entity.ArticleTag;
import com.HappyScrolls.entity.Tag;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static com.HappyScrolls.entity.QArticle.article;
import static com.HappyScrolls.entity.QArticleTag.articleTag;
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


    @Override
    public List<Article> stickerbatch(LocalDate date, Integer lastpage) {
        return jpaQueryFactory
                .selectFrom(article)
                .where(article.createDate.eq(date.minusDays(1)), article.viewCount.gt(100),article.id.gt(lastpage.longValue()))
                .limit(5)
                .fetch();
    }

    @Override
    public List<Article> findByTagPaging(Long lastindex, Tag tag) {
        List<Article> articles= jpaQueryFactory.selectFrom(article)
                .innerJoin(article,articleTag.article)
                .fetchJoin()
                .where(article.id.gt(lastindex))
                .limit(100)
                .fetch();
        return jpaQueryFactory
                .selectFrom(article)
                .innerJoin(article.member, member)
                .fetchJoin()
                .where(article.in(articles))
                .fetch();
    }
}
