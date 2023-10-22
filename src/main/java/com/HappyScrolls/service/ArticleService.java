package com.HappyScrolls.service;

import com.HappyScrolls.adaptor.ArticleAdaptor;
import com.HappyScrolls.adaptor.MemberAdaptor;
import com.HappyScrolls.adaptor.TagAdaptor;
import com.HappyScrolls.dto.ArticleDTO;
import com.HappyScrolls.dto.TagDTO;
import com.HappyScrolls.entity.Article;
import com.HappyScrolls.entity.ArticleTag;
import com.HappyScrolls.entity.Member;
import com.HappyScrolls.entity.Tag;
import com.HappyScrolls.repository.ArticleRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {


    @Autowired
    private MemberAdaptor memberAdaptor;

    @Autowired
    private TagAdaptor tagAdaptor;

    @Autowired
    private ArticleAdaptor articleAdaptor;


    public Long articleCreate(Member member, ArticleDTO.Request request) {
        Article article=request.toEntity();
        article.setMember(member);

        return articleAdaptor.articleCreate(article);
    }



    public ArticleDTO.DetailResponse articleRetrieve(Long id) {
        return ArticleDTO.DetailResponse.toResponseDto(articleAdaptor.retrieveArticle(id));
    }

    public Long articleEdit(Member member, ArticleDTO.Edit request) {

        return articleAdaptor.articleEdit(member,request);
    }

    public void articleDelete(Member member,Long id) {

        articleAdaptor.articleDelete(member,id);
    }

    public List<ArticleDTO.ListResponse>  userArticleRetrieve(String email) {
        Member findMember = memberAdaptor.memberFind(email);

        return ArticleDTO.ListResponse.toResponseDtoList(articleAdaptor.userArticleRetrieve(findMember)) ;
    }





//    //쓰지 않는 페이징 메소드 : 성능 비교용
//    public List<ArticleDTO.ListResponse> articleRetrievePaging(PageRequest pageRequest) {
//        Page<Article> pages = articleRepository.findAll(pageRequest);
//        List<Article> articles=pages.getContent();
//        return ArticleDTO.ListResponse.toResponseDtoList(articles);
//
//    }



    //기능 개선
//    public List<Article> articleRetrieveByTagList( TagDTO.ListRequest request) {
//        List<Tag> tags = new ArrayList<>();
//        for (String tag : request.getTags()) {
//            tags.add(tagService.tagFind(tag));
//        }
//        List<ArticleTag> articleTags = tagService.articlrTagRetrieveByTagList(tags);
//
//        return articleTags.stream()
//                .map(articleTag -> articleTag.getArticle())
//                .collect(Collectors.toList());
//    }

    @CircuitBreaker(name = "circuitbreaker_test", fallbackMethod = "retrieveAllPagingFallBack")
    @Cacheable(cacheNames = "zeropagingarticles", key = "#root.target + #root.methodName",  cacheManager = "cacheManager" )
    @Transactional(readOnly = true)
    public List<ArticleDTO.ListResponse> retrieveAllPaging(Long lastId, Integer limit) {
        return ArticleDTO.ListResponse.toResponseDtoList(articleAdaptor.retrieveByPaging(lastId, limit));
    }

    private List<ArticleDTO.ListResponse> retrieveAllPagingFallBack(Long lastId, Integer limit,Throwable e) {
        return ArticleDTO.ListResponse.toResponseDtoList(articleAdaptor.retrieveByPaging(lastId, limit));
    }



    public List<ArticleDTO.ListResponse> articleRetrieveByTagList(TagDTO.ListRequest request) {
        List<Tag> tags = tagAdaptor.tagsFind(request.getTags());
        return ArticleDTO.ListResponse.toResponseDtoList(articleAdaptor.articleRetrieveByTagList(request.getLastid(), tags));
    }

    //안쓰는 코드
//    public List<Article> articleRetrievePagingWithCoveringIndex(Integer page, Integer limit) {
//        List<Article> articles = articleRepository.coveringPaging(page, limit);
//
//        return articles;
//    }

    public List<ArticleDTO.ListResponse> search(Long lastindex, Integer limit,String param) {

        return ArticleDTO.ListResponse.toResponseDtoList(articleAdaptor.search(lastindex, limit,param));
    }


}
