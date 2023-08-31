package com.HappyScrolls.repository;

import com.HappyScrolls.entity.Article;

import java.util.List;

public interface ArticleCustomRepository {

    List<Article> zeroOffsetPaging(Long startIdx,Integer limitPage);

    List<Article> coveringPaging(Integer page, Integer limit);
}
