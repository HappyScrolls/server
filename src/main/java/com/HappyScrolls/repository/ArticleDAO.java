package com.HappyScrolls.repository;

import com.HappyScrolls.entity.Article;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleDAO {

    @Query(value = "SELECT Article.id,member_id FROM Article JOIN Member ON Article.member_id=Member.id where Article.id> :lastindex limit :limit", nativeQuery = true)
    public List<Article> zeroOffsetPaging(@Param("lastindex") Long lastIndex, @Param("limit") Integer limit);
}
