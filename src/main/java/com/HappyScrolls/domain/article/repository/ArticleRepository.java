package com.HappyScrolls.domain.article.repository;

import com.HappyScrolls.domain.article.entity.Article;
import com.HappyScrolls.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ArticleRepository extends JpaRepository<Article,Long>,ArticleCustomRepository {

    List<Article> findAllByMember(Member member);
}

