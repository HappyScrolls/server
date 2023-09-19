package com.HappyScrolls.repository;

import com.HappyScrolls.entity.ArticleTag;
import com.HappyScrolls.entity.Tag;

import java.util.List;

public interface ArticleTagCustomRepository {
    List<ArticleTag> findByTagPaging(Long lastindex, Tag tag);
}
