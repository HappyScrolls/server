package com.HappyScrolls.repository;

import com.HappyScrolls.entity.Article;
import com.HappyScrolls.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByArticle(Article article);
}
