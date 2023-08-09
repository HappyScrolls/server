package com.HappyScrolls.repository;

import com.HappyScrolls.entity.Article;
import com.HappyScrolls.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Tuple;
import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article,Long> {
    Optional<Article> findByMember(Member findMember);

    List<Article> findAllByMember(Member findMember);
}
