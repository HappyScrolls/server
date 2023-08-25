package com.HappyScrolls.service;

import com.HappyScrolls.dto.ArticleDTO;
import com.HappyScrolls.dto.TagDTO;
import com.HappyScrolls.entity.Article;
import com.HappyScrolls.entity.Member;
import com.HappyScrolls.exception.NoAuthorityExceoption;
import com.HappyScrolls.exception.UserNotFoundException;
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
    private MemberService memberService;

    @Autowired
    private TagService tagService;

    public ArticleDTO.Response articleCreate(Member member, ArticleDTO.Request request) {
        Article article = request.toEntity();
        article.setMember(member);


        articleRepository.save(article);
        tagService.tagCreate(article, request.getTags());

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
        List<TagDTO.Response> tags = tagService.tagsRetrieve(article);

        return ArticleDTO.Response.builder()
                .id(article.getId())
                .title(article.getTitle())
                .body(article.getBody())
                .tags(tags)
                .build();
    }

    public ArticleDTO.Response articleEdit(Member member,ArticleDTO.Edit request) {


        Article article = articleRepository.findById(request.getId()).orElseThrow(() -> new NoSuchElementException(String.format("article[%s] 게시글을 찾을 수 없습니다", request.getId()))); //%s?

        if (!article.getMember().equals(member)) {
            throw new NoAuthorityExceoption("수정 권한이 없습니다. 본인 소유의 글만 수정 가능합니다.");
        }

        article.edit(request);

        Article editedArticle =articleRepository.save(article);

        return ArticleDTO.Response.builder()
                .id(editedArticle.getId())
                .title(editedArticle.getTitle())
                .body(editedArticle.getBody())
                .build();
    }

    public void articleDelete(Member member,Long id) {


        Article article = articleRepository.findById(id).orElseThrow(()-> new NoSuchElementException(String.format("article[%s] 게시글을 찾을 수 없습니다", id))); //%s?

        if (!article.getMember().equals(member)) {
            throw new NoAuthorityExceoption("삭제 권한이 없습니다. 본인 소유의 글만 삭제  가능합니다.");
        }

        articleRepository.delete(article);
    }

    public List<ArticleDTO.Response>  userArticleRetrieve(String email) {
        Member findMember = memberService.memberFind(email);

        List<Article> articles = articleRepository.findAllByMember(findMember);

        List<ArticleDTO.Response> response = new ArrayList<>();

        for (Article article : articles) {
            response.add(ArticleDTO.Response.builder()
                    .id(article.getId())
                    .title(article.getTitle())
                    .body(article.getBody())
                    .build());
        }

        return response;
    }

    public Article articleFind(Long postId) {
        return articleRepository.findById(postId).orElseThrow(()-> new NoSuchElementException(String.format("article[%s] 게시글을 찾을 수 없습니다", postId))); //%s?
    }
}
