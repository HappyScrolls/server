package com.HappyScrolls.domain.article.repository;

import com.HappyScrolls.domain.article.entity.Article;
import com.HappyScrolls.domain.tag.entity.ArticleTag;
import com.HappyScrolls.domain.tag.entity.Tag;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import static com.HappyScrolls.domain.article.entity.QArticle.article;
import static com.HappyScrolls.domain.member.entity.QMember.member;
import static com.HappyScrolls.domain.tag.entity.QArticleTag.articleTag;
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
    public List<Article> findByTagPaging(Long lastindex, Tag tag) {
        List<ArticleTag> articleTags=  jpaQueryFactory
                .selectFrom(articleTag)
                .innerJoin(articleTag.article,article)
                .fetchJoin()
                .where(article.id.gt(lastindex),articleTag.tag.eq(tag))
                .limit(100)
                .fetch();
        List<Long> ids = articleTags.stream()
                .map(articleTag -> articleTag.getArticle().getId())
                .collect(Collectors.toList());
        return jpaQueryFactory
                .selectFrom(article)
                .innerJoin(article.member, member)
                .fetchJoin()
                .where(article.id.in(ids))
                .fetch();
    }


    @Override
    public List<Article> findByTagListPaging(Long lastindex, List<Tag> tags) {
        List<ArticleTag> articleTags=  jpaQueryFactory
                .selectFrom(articleTag)
                .innerJoin(articleTag.article,article)
                .fetchJoin()
                .where(article.id.gt(lastindex),articleTag.tag.in(tags))
                .limit(100)
                .fetch();
        List<Long> ids = articleTags.stream()
                .map(articleTag -> articleTag.getArticle().getId())
                .collect(Collectors.toList());
        return jpaQueryFactory
                .selectFrom(article)
                .innerJoin(article.member, member)
                .fetchJoin()
                .where(article.id.in(ids))
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
    public List<Article> search(Long lastindex, Integer limit, String param) {
        return jpaQueryFactory
                .selectFrom(article)
                .innerJoin(article.member, member)
                .fetchJoin()
                .where(article.title.contains(param),article.id.gt(lastindex))
                .limit(limit)
                .fetch();
    }
}
