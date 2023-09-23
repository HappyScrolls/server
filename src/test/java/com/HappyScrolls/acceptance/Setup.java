package com.HappyScrolls.acceptance;

import com.HappyScrolls.dto.ArticleDTO;
import com.HappyScrolls.entity.Article;
import com.HappyScrolls.entity.Member;
import com.HappyScrolls.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Setup {
    @Autowired
    private ArticleRepository articleRepository;

    public Long saveArticle() {
        ArticleDTO.Request request = ArticleDTO.Request.builder().title("제목").body("내용").tags(null).build();
        Article article = request.toEntity();
        articleRepository.save(article);
        return article.getId();
    }
}