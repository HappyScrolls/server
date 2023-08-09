package com.HappyScrolls.service;

import com.HappyScrolls.dto.ArticleDTO;
import com.HappyScrolls.entity.Article;
import com.HappyScrolls.entity.Member;
import com.HappyScrolls.exception.NoAuthorityExceoption;
import com.HappyScrolls.repository.ArticleRepository;
import com.HappyScrolls.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private MemberRepository memberRepository;

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
        Article article = articleRepository.findById(id).orElseThrow(()-> new NoSuchElementException(String.format("article[%s] 게시글을 찾을 수 없습니다", id))); //%s?


        return ArticleDTO.Response.builder()
                .id(article.getId())
                .title(article.getTitle())
                .body(article.getBody())
                .build();
    }

    public ArticleDTO.Response articleEdit(Member member,ArticleDTO.Edit request) {


        Article article = articleRepository.findById(request.getId()).orElseThrow(() -> new NoSuchElementException(String.format("article[%s] 게시글을 찾을 수 없습니다", request.getId()))); //%s?

        if (!article.getMember().equals(member)) {
            throw new NoAuthorityExceoption("수정 권한이 없습니다. 본인 소유의 글만 수정 가능합니다.");
        }

        article.edit(request);

        articleRepository.save(article);

        return ArticleDTO.Response.builder()
                .id(article.getId())
                .title(article.getTitle())
                .body(article.getBody())
                .build();
    }

    public void articleDelete(Member member,Long id) {

        //유저 검증 로직


        Article article = articleRepository.findById(id).orElseThrow(()-> new NoSuchElementException(String.format("article[%s] 게시글을 찾을 수 없습니다", id))); //%s?

        articleRepository.delete(article);
    }

    public ArticleDTO.Response userArticleRetrieve(String email) {
        Member findMember = memberRepository.findByEmail(email).get();
        Article article = articleRepository.findByMember(findMember).get();

        return ArticleDTO.Response.builder()
                .id(article.getId())
                .title(article.getTitle())
                .body(article.getBody())
                .build();
    }
}
