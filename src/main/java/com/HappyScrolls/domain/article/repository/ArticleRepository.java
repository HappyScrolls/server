package com.HappyScrolls.domain.article.repository;

import com.HappyScrolls.domain.article.entity.Article;
import com.HappyScrolls.domain.member.entity.Member;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;


public interface ArticleRepository extends Repository<Article,Long>,ArticleCustomRepository {

    List<Article> findAllByMember(Member member);

    Article  save(Article entity);

    Optional<Article> findById(Long id);

    void delete(Article entity);

    Page<Article> findAll(Pageable pageable);

    List<Article> findAll();
}

