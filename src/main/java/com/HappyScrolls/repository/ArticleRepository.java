package com.HappyScrolls.repository;

import com.HappyScrolls.entity.Article;
import com.HappyScrolls.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.Tuple;
import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article,Long> ,ArticleDAO{


    List<Article> findAll();


    List<Article> findAllByMemberId(Long id);
}

