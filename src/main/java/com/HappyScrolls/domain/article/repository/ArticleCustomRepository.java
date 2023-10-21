package com.HappyScrolls.domain.article.repository;

import com.HappyScrolls.domain.article.entity.Article;
import com.HappyScrolls.domain.tag.entity.Tag;

import java.time.LocalDate;
import java.util.List;

public interface ArticleCustomRepository {

    List<Article> zeroOffsetPaging(Long startIdx,Integer limitPage);

    List<Article> coveringPaging(Integer page, Integer limit);

    List<Article> stickerbatch(LocalDate date,Integer lastpage);

    List<Article> findByTagPaging(Long lastindex, Tag tag);

    List<Article> findByTagListPaging(Long lastindex, List<Tag> tags);

    List<Article> search(Long lastindex, Integer limit, String param);
}
