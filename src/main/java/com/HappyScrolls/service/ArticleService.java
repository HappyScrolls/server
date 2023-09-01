package com.HappyScrolls.service;

import com.HappyScrolls.dto.ArticleDTO;
import com.HappyScrolls.dto.TagDTO;
import com.HappyScrolls.dto.testRepo;
import com.HappyScrolls.entity.Article;
import com.HappyScrolls.entity.ArticleTag;
import com.HappyScrolls.entity.Member;
import com.HappyScrolls.entity.Tag;
import com.HappyScrolls.exception.NoAuthorityExceoption;
import com.HappyScrolls.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private TagService tagService;



    public Article articleCreate(Member member, ArticleDTO.Request request) {
        Article article = request.toEntity();
        article.setMember(member);

        articleRepository.save(article);
        tagService.tagCreate(article, request.getTags());
        return article;
    }

//    public List<ArticleDTO.ListResponse> articleRetrieveAll() {
//        List<Article> allArticles = articleRepository.findAll();
//
//        List<ArticleDTO.ListResponse> detailResponse = new ArrayList<>();
//
//        for (Article article : allArticles) {
//            detailResponse.add(ArticleDTO.ListResponse.builder()
//                    .id(article.getId())
//                    .title(article.getTitle())
//                    .member(article.getMember().getNickname())
//                    .build());
//        }
//
//        return detailResponse;
//    }

    public Article articleRetrieve(Long id) {
        Article article = articleRepository.findById(id).orElseThrow(()-> new NoSuchElementException(String.format("article[%s] 게시글을 찾을 수 없습니다", id))); //%s?
        List<TagDTO.Response> tags = tagService.tagsRetrieve(article);

        return article;
    }

    public Article articleEdit(Member member, ArticleDTO.Edit request) {

        Article article = articleRepository.findById(request.getId()).orElseThrow(() -> new NoSuchElementException(String.format("article[%s] 게시글을 찾을 수 없습니다", request.getId()))); //%s?


        if (!article.getMember().equals(member)) {
            throw new NoAuthorityExceoption("수정 권한이 없습니다. 본인 소유의 글만 수정 가능합니다.");
        }
        article.edit(request);
        articleRepository.save(article);
        return article;
    }

    public void articleDelete(Member member,Long id) {
        Article article = articleRepository.findById(id).orElseThrow(()-> new NoSuchElementException(String.format("article[%s] 게시글을 찾을 수 없습니다", id))); //%s?

        if (!article.getMember().equals(member)) {
            throw new NoAuthorityExceoption("삭제 권한이 없습니다. 본인 소유의 글만 삭제  가능합니다.");
        }
        articleRepository.delete(article);
    }

    public List<Article>  userArticleRetrieve(String email) {
        Member findMember = memberService.memberFind(email);

        List<Article> articles = articleRepository.findAllByMember(findMember);
        return articles;

    }

    public Article articleFind(Long postId) {
        return articleRepository.findById(postId).orElseThrow(()-> new NoSuchElementException(String.format("article[%s] 게시글을 찾을 수 없습니다", postId))); //%s?
    }

    public List<Article> articleRetrieveByTag(String tag) {
        Tag findTag = tagService.tagFind(tag);
        List<ArticleTag> articleTags = tagService.articlrTagRetrieveByTag(findTag);

        return articleTags.stream()
                .map(articleTag -> articleTag.getArticle())
                .collect(Collectors.toList());

    }

    public List<Article> articleRetrievePaging(PageRequest pageRequest) {
        Page<Article> pages = articleRepository.findAll(pageRequest);


        return pages.getContent();

    }


    public List<Article> articleRetrievePagingWithZeroIndex(Long lastindex, Integer limit) {
        List<Article> response = articleRepository.zeroOffsetPaging(lastindex,limit);

        return response;
    }
    public void increaseViewCount(Article article) {
        article.increaseViewCount();
        articleRepository.save(article);
    }

    public List<Article> articleRetrieveByTagList( TagDTO.ListRequest request) {
        List<Tag> tags = new ArrayList<>();
        for (String tag : request.getTags()) {
            tags.add(tagService.tagFind(tag));
        }
        List<ArticleTag> articleTags = tagService.articlrTagRetrieveByTagList(tags);

        return articleTags.stream()
                .map(articleTag -> articleTag.getArticle())
                .collect(Collectors.toList());
    }

    public List<Article> articleRetrievePagingWithZeroOffset(Long lastindex, Integer limit) {

        List<Article> articles = articleRepository.zeroOffsetPaging(lastindex, limit);

        return articles;
    }

    public List<Article> articleRetrievePagingWithCoveringIndex(Integer page, Integer limit) {
        List<Article> articles = articleRepository.coveringPaging(page, limit);

        return articles;
    }

}
