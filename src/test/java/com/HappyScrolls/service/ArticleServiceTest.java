package com.HappyScrolls.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import com.HappyScrolls.adaptor.ArticleAdaptor;
import com.HappyScrolls.adaptor.MemberAdaptor;
import com.HappyScrolls.adaptor.TagAdaptor;
import com.HappyScrolls.dto.ArticleDTO;
import com.HappyScrolls.dto.TagDTO;
import com.HappyScrolls.entity.Article;
import com.HappyScrolls.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayWithSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ArticleServiceTest {

    @InjectMocks
    private ArticleService articleService;

    @Mock
    private ArticleAdaptor articleAdaptor;

    @Mock
    private MemberAdaptor memberAdaptor;

    @Mock
    private TagAdaptor tagAdaptor;

    @Test
    @DisplayName("게시글 생성 테스트")
    void 게시글_생성_테스트() {
        Member member = new Member();
        ArticleDTO.Request request = new ArticleDTO.Request();

        when(articleAdaptor.articleCreate(any())).thenReturn(1L);

        Long result = articleService.articleCreate(member, request);

        assertEquals(1L, result);
    }

    @Test
    @DisplayName("게시글 검색 테스트")
    void 게시글_검색_테스트() {
        Article article = Article.builder().id(1l).build();
        when(articleAdaptor.retrieveArticle(any())).thenReturn(article);

        ArticleDTO.DetailResponse result = articleService.articleRetrieve(1L);

        assertEquals(result.getId(),article.getId());
    }

    @Test
    @DisplayName("게시글 수정 테스트")
    void 게시글_수정_테스트() {
        ArticleDTO.Edit request = ArticleDTO.Edit.builder().id(1l).build();

        when(articleAdaptor.articleEdit(any(), any())).thenReturn(1L);

        Long result = articleService.articleEdit(new Member(), request);

        assertEquals(1L, result);
    }

    @Test
    @DisplayName("게시글 삭제 테스트")
    void 게시글_삭제_테스트() {

        articleService.articleDelete( new Member(), 1L);

        verify(articleAdaptor).articleDelete(any(), any());
    }

    @Test
    @DisplayName("사용자 게시글 검색 테스트")
    void 사용자_게시글_검색_테스트() {
        Member member = Member.builder().email("test@email.com").build();
        Article article1= Article.builder().id(1l).title("1").member(member).build();
        Article article2 = Article.builder().id(2l).title("2").member(member).build();
        when(memberAdaptor.memberFind(any())).thenReturn(member);
        when(articleAdaptor.userArticleRetrieve(any())).thenReturn(List.of(article1,article2));

        List<ArticleDTO.ListResponse> result = articleService.userArticleRetrieve("test@email.com");

        assertEquals(result.size(),2);
        assertEquals(result.get(0).getId(),1l);
    }

    @Test
    @DisplayName("전체 게시글 페이징 검색 테스트")
    void 전체_게시글_페이징_검색_테스트() {
        Member member = Member.builder().email("test@email.com").build();
        Article article1= Article.builder().id(1l).title("1").member(member).build();
        Article article2 = Article.builder().id(2l).title("2").member(member).build();
        when(articleAdaptor.retrieveByPaging(any(), any())).thenReturn(List.of(article1,article2));

        List<ArticleDTO.ListResponse> result = articleService.retrieveAllPaging(1L, 10);

        assertEquals(result.size(),2);
        assertEquals(result.get(0).getId(),1l);
    }

    @Test
    @DisplayName("태그로 게시글 검색 테스트")
    void 태그로_게시글_검색_테스트() {
        Member member = Member.builder().email("test@email.com").build();
        Article article1= Article.builder().id(1l).title("1").member(member).build();
        Article article2 = Article.builder().id(2l).title("2").member(member).build();
        when(tagAdaptor.tagsFind(any())).thenReturn(new ArrayList<>());
        when(articleAdaptor.articleRetrieveByTagList(any(), any())).thenReturn(List.of(article1,article2));

        TagDTO.ListRequest request = new TagDTO.ListRequest();
        List<ArticleDTO.ListResponse> result = articleService.articleRetrieveByTagList(request);

        assertEquals(result.size(),2);
        assertEquals(result.get(0).getId(),1l);
        assertEquals(result.get(1).getId(),2l);

    }

    @Test
    @DisplayName("게시글 검색 테스트")
    void 게시글_검색() {
        Member member = Member.builder().email("test@email.com").build();
        Article article= Article.builder().id(1l).title("test").member(member).build();
        when(articleAdaptor.search(any(), any(), any())).thenReturn(List.of(article));

        List<ArticleDTO.ListResponse> result = articleService.search(1L, 10, "test");

        assertEquals(result.size(),1);
        assertEquals(result.get(0).getTitle(),"test");
    }
}