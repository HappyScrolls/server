package com.HappyScrolls.domain.tag.repository;

import com.HappyScrolls.domain.tag.entity.ArticleTag;
import com.HappyScrolls.domain.tag.entity.Tag;

import java.util.List;

public interface ArticleTagCustomRepository {
    List<ArticleTag> findByTagPaging(Long lastindex, Tag tag);
}
