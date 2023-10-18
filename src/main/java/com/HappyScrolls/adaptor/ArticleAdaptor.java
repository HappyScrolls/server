package com.HappyScrolls.adaptor;


import com.HappyScrolls.dto.ArticleDTO;
import com.HappyScrolls.dto.TagDTO;
import com.HappyScrolls.entity.Article;
import com.HappyScrolls.entity.ArticleTag;
import com.HappyScrolls.entity.Member;
import com.HappyScrolls.entity.Tag;
import com.HappyScrolls.exception.NoAuthorityExceoption;
import com.HappyScrolls.exception.NoResultException;
import com.HappyScrolls.repository.ArticleRepository;
import com.HappyScrolls.service.MemberService;
import com.HappyScrolls.service.TagService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Adaptor
public class ArticleAdaptor {
    @Autowired
    private  ArticleRepository articleRepository;


    @Autowired
    private TagAdaptor tagAdaptor;



    public List<Article> retrieveByPaging(Long lastId, Integer limitPage){
        List<Article> result =  articleRepository.zeroOffsetPaging(lastId, limitPage);
        if(result.isEmpty()) throw new NoResultException(String.format("게시글 조회 결과가 비었습니다. lastId:[%s]", lastId));

        return result;
    }

    public Article retrieveArticle(Long id) {
        Article article = articleRepository.findById(id).orElseThrow(()-> new NoSuchElementException(String.format("article[%s] 게시글을 찾을 수 없습니다", id))); //%s?
        return article;
    }



    public Long articleCreate(Article request) {
        return articleRepository.save(request).getId();

    }


    public Long articleEdit(Member member, ArticleDTO.Edit request) {

        Article article = articleRepository.findById(request.getId()).orElseThrow(() -> new NoSuchElementException(String.format("article[%s] 게시글을 찾을 수 없습니다", request.getId()))); //%s?


        if (!article.getMember().equals(member)) {
            throw new NoAuthorityExceoption("수정 권한이 없습니다. 본인 소유의 글만 수정 가능합니다.");
        }
        article.edit(request);
        articleRepository.save(article);
        return article.getId();
    }

    public void articleDelete(Member member,Long id) {
        Article article = articleRepository.findById(id).orElseThrow(()-> new NoSuchElementException(String.format("article[%s] 게시글을 찾을 수 없습니다", id))); //%s?

        if (!article.getMember().equals(member)) {
            throw new NoAuthorityExceoption("삭제 권한이 없습니다. 본인 소유의 글만 삭제  가능합니다.");
        }
        articleRepository.delete(article);
    }

    public List<Article>  userArticleRetrieve(Member member) {

        List<Article> articles = articleRepository.findAllByMember(member);
        return articles;

    }


    public List<Article> articleRetrieveByTagPaging2(Long lastindex, String tag) {

        Tag findTag = tagAdaptor.tagFind(tag);
        List<Article> articles =  articleRepository.findByTagPaging(lastindex,findTag);

        return articles;

    }

    public List<Article> articleRetrieveByTagList(Long lastid,List<Tag> tags) {
        return articleRepository.findByTagListPaging(lastid, tags);
    }

    @Transactional(readOnly = true)
    public List<Article> search(Long lastindex, Integer limit,String param) {

        return articleRepository.search(lastindex, limit,param);

    }

}
