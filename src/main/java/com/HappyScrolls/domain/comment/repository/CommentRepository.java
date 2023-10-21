package com.HappyScrolls.domain.comment.repository;

import com.HappyScrolls.domain.article.entity.Article;
import com.HappyScrolls.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends Repository<Comment,Long> {
    List<Comment> findByArticle(Article article);

    Comment  save(Comment entity);

    Optional<Comment> findById(Long id);

    void delete(Comment entity);
}
