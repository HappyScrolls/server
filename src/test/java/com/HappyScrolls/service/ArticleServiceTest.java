package com.HappyScrolls.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.HappyScrolls.dto.ArticleDTO;
import com.HappyScrolls.entity.Article;
import com.HappyScrolls.entity.Member;
import com.HappyScrolls.exception.NoAuthorityExceoption;
import com.HappyScrolls.repository.ArticleRepository;
import com.HappyScrolls.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayWithSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ArticleServiceTest {

    @InjectMocks
    private ArticleService articleService;

    @Mock
    private ArticleRepository articleRepository;



    private static final Long ARTICLE_ID = 1L;
    private static final Long USER_ID = 1L;





    @Test
    @DisplayName("게시물 작성 기능이 제대로 동작하는지 확인")
    void 게시물_작성_성공_테스트() {

        Member member = Member.builder().id(USER_ID).email("chs98412@naver,com").nickname("hyuksoon").thumbnail("img").build();
        ArticleDTO.Request request = ArticleDTO.Request.builder().title("제목").body("내용").build();

        Article article = request.toEntity();
        article.setMember(member);
        when(articleRepository.save(any())).thenReturn(article);


        ArticleDTO.Response response=   articleService.articleCreate(member, request);

        verify(articleRepository).save(article);
        assertThat(response.getTitle()).isEqualTo(request.getTitle());
        assertThat(response.getBody()).isEqualTo(request.getBody());
    }

    @Test
    @DisplayName("게시물 전체 조회 기능이 제대로 동작하는지 확인")
    void 게시물_전체조회_성공_테스트() {

        Member member = Member.builder().id(USER_ID).email("chs98412@naver,com").nickname("hyuksoon").thumbnail("img").build();
        Article article1 = new Article(1l, member, "제목1", "내용1");
        Article article2 = new Article(2l, member, "제목2", "내용2");
        Article article3 = new Article(3l, member, "제목3", "내용3");

        List<Article> articles = new ArrayList<>();
        articles.add(article1);
        articles.add(article2);
        articles.add(article3);

        when(articleRepository.findAll()).thenReturn(articles);


        List<ArticleDTO.Response> response = articleService.articleRetrieveAll();

        verify(articleRepository).findAll();


        assertThat(response).isEqualTo(articles.stream()
                .map(article -> ArticleDTO.Response.builder()
                        .id(article.getId())
                        .title(article.getTitle())
                        .body(article.getBody())
                        .build())
                .collect(Collectors.toList()));
    }

    @Test
    @DisplayName("게시글 단건 조회 기능이 제대로 동작하는지 확인")
    void 게시글_단건조회_성공_테스트() {

        Member member = Member.builder().id(USER_ID).email("chs98412@naver,com").nickname("hyuksoon").thumbnail("img").build();
        Article article= new Article(1l, member, "제목1", "내용1");

        when(articleRepository.findById(any())).thenReturn(Optional.of(article));


        ArticleDTO.Response response = articleService.articleRetrieve(1L);

        verify(articleRepository).findById(1L);
        assertThat(response.getId()).isEqualTo(response.getId());
        assertThat(response.getTitle()).isEqualTo(response.getTitle());
        assertThat(response.getBody()).isEqualTo(response.getBody());

    }


    @Test
    @DisplayName("게시글 단건 조회 기능이 조회를 할 수 없을 때 예외처리를 하는지 확인")
    void 게시글_단건조회_예외_테스트() {
        Long testId = 1L;
        when(articleRepository.findById(any())).thenReturn(Optional.empty());

        // when
        assertThrows(NoSuchElementException.class, () -> articleService.articleRetrieve(testId));

        // then
        verify(articleRepository).findById(testId);
    }


    @Test
    @DisplayName("게시글 수정 기능이  제대로 동작하는지 확인")
    void 게시글_수정_성공_테스트() {
        Member member = Member.builder().id(USER_ID).email("chs98412@naver,com").nickname("hyuksoon").thumbnail("img").build();
        Article article= new Article(1L, member, "제목1", "내용1");
        when(articleRepository.findById(any())).thenReturn(Optional.of(article));


        ArticleDTO.Edit request = ArticleDTO.Edit.builder().id(1L).title("제목_수정").body("내용_수정").build();
        article.edit(request);
        when(articleRepository.save(any())).thenReturn(article);


        ArticleDTO.Response response = articleService.articleEdit(member, request);



        verify(articleRepository).findById(1L);
        verify(articleRepository).save(article);
        assertThat(response).isEqualTo(ArticleDTO.Response.builder().id(article.getId()).title(article.getTitle()).body(article.getBody()).build() );

    }


    @Test
    @DisplayName("게시글 수정 기능에서 게시글  조회를 할 수 없을 때 예외처리를 하는지 확인")
    void 게시글_수정기능_조회오류예외_테스트() {

        Member member = Member.builder().id(USER_ID).email("chs98412@naver,com").nickname("hyuksoon").thumbnail("img").build();
        ArticleDTO.Edit request = ArticleDTO.Edit.builder().id(1L).title("제목_수정").body("내용_수정").build();
        when(articleRepository.findById(any())).thenReturn(Optional.empty());

        // when
        assertThrows(NoSuchElementException.class, () -> articleService.articleEdit(member,request));

        // then
        verify(articleRepository).findById(request.getId());
    }
    @Test
    @DisplayName("게시글 수정 기능에서 본인 소유의 게시글이 아닐 때 예외처리를 하는지 확인")
    void 게시글_수정기능_권한제한예외_테스트() {

        Member member = Member.builder().id(USER_ID).email("chs98412@naver,com").nickname("hyuksoon").thumbnail("img").build();
        Member requestMember = Member.builder().id(USER_ID).email("abc1234@naver,com").nickname("toy").thumbnail("img").build();
        ArticleDTO.Edit request = ArticleDTO.Edit.builder().id(1L).title("제목_수정").body("내용_수정").build();
        Article article= new Article(1L, member, "제목1", "내용1");
        when(articleRepository.findById(any())).thenReturn(Optional.of(article));



        assertThrows(NoAuthorityExceoption.class, () -> articleService.articleEdit(requestMember,request));
        verify(articleRepository).findById(request.getId());
    }


    @Test
    @DisplayName("게시글 삭제 기능이  제대로 동작하는지 확인")
    void 게시글_삭제_성공_테스트() {
        Long testId = 1L;
        Member member = Member.builder().id(USER_ID).email("chs98412@naver,com").nickname("hyuksoon").thumbnail("img").build();
        Article article= new Article(testId, member, "제목1", "내용1");
        when(articleRepository.findById(any())).thenReturn(Optional.of(article));




        articleService.articleDelete(member, testId);



        verify(articleRepository).findById(testId);
        verify(articleRepository).delete(article);
    }

    @Test
    @DisplayName("게시글 삭제 기능에서 게시글  조회를 할 수 없을 때 예외처리를 하는지 확인")
    void 게시글_삭제기능_조회오류예외_테스트() {
        Long testId = 1L;
        Member member = Member.builder().id(USER_ID).email("chs98412@naver,com").nickname("hyuksoon").thumbnail("img").build();
        when(articleRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> articleService.articleDelete(member,testId));

        verify(articleRepository).findById(testId);
    }

    @Test
    @DisplayName("게시글 삭제기능에서 본인 소유의 게시글이 아닐 때 예외처리를 하는지 확인")
    void 게시글_삭제기능_권한제한예외_테스트() {
        Long testId = 1L;
        Member member = Member.builder().id(USER_ID).email("chs98412@naver,com").nickname("hyuksoon").thumbnail("img").build();
        Member requestMember = Member.builder().id(USER_ID).email("abc1234@naver,com").nickname("toy").thumbnail("img").build();
        Article article= new Article(testId, member, "제목1", "내용1");
        when(articleRepository.findById(any())).thenReturn(Optional.of(article));



        assertThrows(NoAuthorityExceoption.class, () -> articleService.articleDelete(requestMember,testId));
        verify(articleRepository).findById(testId);
    }
}