package com.HappyScrolls.service;

import com.HappyScrolls.dto.ArticleDTO;
import com.HappyScrolls.entity.Article;
import com.HappyScrolls.entity.Member;
import com.HappyScrolls.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    public ArticleDTO.Response articleCreate(Member member, ArticleDTO.Request request) {
        Article article = request.toEntity();
        article.setMember(member);

        articleRepository.save(article);
        return ArticleDTO.Response.builder()
                .id(article.getId())
                .title(article.getTitle())
                .body(article.getBody())
                .build();
    }

    public List<ArticleDTO.Response> articleRetrieveAll() {
        List<Article> allArticles = articleRepository.findAll();

        List<ArticleDTO.Response> response = new ArrayList<>();

        for (Article article : allArticles) {
            response.add(ArticleDTO.Response.builder()
                    .id(article.getId())
                    .title(article.getTitle())
                    .body(article.getBody())
                    .build());
        }

        return response;
    }

    public ArticleDTO.Response articleRetrieve(Long id) {
        Article article = articleRepository.findById(id).get();

        return ArticleDTO.Response.builder()
                .id(article.getId())
                .title(article.getTitle())
                .body(article.getBody())
                .build();
    }

    public ArticleDTO.Response articleEdit(ArticleDTO.edit request) {
        Article article = articleRepository.findById(request.getId()).get();

        article.edit(request);

        articleRepository.save(article);

        return ArticleDTO.Response.builder()
                .id(article.getId())
                .title(article.getTitle())
                .body(article.getBody())
                .build();
    }

    public void articleDelete(Long id) {
        Article article = articleRepository.findById(id).get();

        articleRepository.delete(article);
    }
}
