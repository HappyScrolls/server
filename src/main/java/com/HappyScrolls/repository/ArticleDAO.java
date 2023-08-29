package com.HappyScrolls.repository;

import com.HappyScrolls.dto.ArticleDTO;
import com.HappyScrolls.entity.Article;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.HappyScrolls.dto.testRepo;

import java.util.List;

public interface ArticleDAO {

    @Query(value = "SELECT Article.id as id, Article.title as title, Article.body as body ,Article.member_id as memberId, Member.nickname as nickname FROM Article INNER JOIN Member ON Article.member_id=Member.id where Article.id> :lastindex limit :limit", nativeQuery = true)
    public List<testRepo> zeroOffsetPaging(@Param("lastindex") Long lastIndex, @Param("limit") Integer limit);
}
