package com.HappyScrolls.domain.article.adaptor;


import com.HappyScrolls.config.Adaptor;
import com.HappyScrolls.domain.tag.adaptor.TagAdaptor;
import com.HappyScrolls.domain.article.dto.ArticleDTO;
import com.HappyScrolls.domain.article.entity.Article;
import com.HappyScrolls.domain.member.entity.Member;
import com.HappyScrolls.domain.tag.entity.Tag;
import com.HappyScrolls.exception.NoAuthorityException;
import com.HappyScrolls.exception.NoResultException;
import com.HappyScrolls.domain.article.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

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

        Article article = retrieveArticle(request.getId());
        article.edit(member,request);
        articleRepository.save(article);    //없어도 되지 않나??
        return article.getId();
    }

    public void articleDelete(Member member,Long id) {
        Article article = retrieveArticle(id);
        article.checkPermission(member);
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
