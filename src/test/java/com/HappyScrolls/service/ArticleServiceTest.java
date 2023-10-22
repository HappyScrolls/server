package com.HappyScrolls.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.HappyScrolls.dto.ArticleDTO;
import com.HappyScrolls.entity.Article;
import com.HappyScrolls.entity.Member;
import com.HappyScrolls.entity.Sticker;
import com.HappyScrolls.exception.NoAuthorityExceoption;
import com.HappyScrolls.repository.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayWithSize;
import static org.hamcrest.Matchers.is;

@ExtendWith(MockitoExtension.class)
public class ArticleServiceTest {

    @InjectMocks
    private ArticleService articleService;

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private MemberService memberService;

    @Mock
    private TagService tagService;

    private static final Long ARTICLE_ID = 1L;
    private static final Long USER_ID = 1L;





    @Test
    @DisplayName("게시물 작성 기능이 제대로 동작하는지 확인")
    void 게시물_작성_성공_테스트() {


        Member member = Member.builder().id(1l).email("chs98412@naver,com").nickname("hyuksoon").thumbnail("img").build();
        ArticleDTO.Request request = ArticleDTO.Request.builder().title("제목").body("내용").tags(null).build();
        Article article = request.toEntity();
        article.setId(1l);
        article.setMember(member);

        when(articleRepository.save(any())).thenReturn(article);

        Long response =   articleService.articleCreate(member, request);

        verify(articleRepository).save(any());
        assertThat(response).isEqualTo(article.getId());
    }


    @Test
    @DisplayName("게시글 단건 조회 기능이 제대로 동작하는지 확인")
    void 게시글_단건조회_성공_테스트() {

        Member member = Member.builder().id(USER_ID).email("chs98412@naver,com").nickname("hyuksoon").thumbnail("img").build();
        Article article = Article.builder().id(1l).member(member).title("제목1").body("내용1").viewCount(0).createDate(LocalDate.now()).sticker(Sticker.NEWHIT).build();
        when(articleRepository.findById(any())).thenReturn(Optional.of(article));


        Article response = articleService.articleRetrieve(1L);

        verify(articleRepository).findById(1L);
        assertThat(response).isEqualTo(article);

    }


    @Test
    @DisplayName("게시글 단건 조회 기능이 조회를 할 수 없을 때 예외처리를 하는지 확인")
    void 게시글_단건조회_예외_테스트() {
        Long testId = 1L;
        when(articleRepository.findById(any())).thenReturn(Optional.empty());


        assertThrows(NoSuchElementException.class, () -> articleService.articleRetrieve(testId));


        verify(articleRepository).findById(testId);
    }


    @Test
    @DisplayName("게시글 수정 기능이  제대로 동작하는지 확인")
    void 게시글_수정_성공_테스트() {
        Member member = Member.builder().id(USER_ID).email("chs98412@naver,com").nickname("hyuksoon").thumbnail("img").build();
        Article article = Article.builder().id(1l).member(member).title("제목1").body("내용1").viewCount(0).createDate(LocalDate.now()).sticker(Sticker.NEWHIT).build();
        when(articleRepository.findById(any())).thenReturn(Optional.of(article));


        ArticleDTO.Edit request = ArticleDTO.Edit.builder().id(1L).title("제목_수정").body("내용_수정").build();
        article.edit(request);
        when(articleRepository.save(any())).thenReturn(article);

        Long response = articleService.articleEdit(member, request);


        verify(articleRepository).findById(1L);
        verify(articleRepository).save(article);
        assertThat(response).isEqualTo(article.getId());

    }


    @Test
    @DisplayName("게시글 수정 기능에서 게시글  조회를 할 수 없을 때 예외처리를 하는지 확인")
    void 게시글_수정기능_조회오류예외_테스트() {

        Member member = Member.builder().id(USER_ID).email("chs98412@naver,com").nickname("hyuksoon").thumbnail("img").build();
        ArticleDTO.Edit request = ArticleDTO.Edit.builder().id(1L).title("제목_수정").body("내용_수정").build();
        when(articleRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> articleService.articleEdit(member,request));

        verify(articleRepository).findById(request.getId());
    }
    @Test
    @DisplayName("게시글 수정 기능에서 본인 소유의 게시글이 아닐 때 예외처리를 하는지 확인")
    void 게시글_수정기능_권한제한예외_테스트() {

        Member member = Member.builder().id(USER_ID).email("chs98412@naver,com").nickname("hyuksoon").thumbnail("img").build();
        Member requestMember = Member.builder().id(USER_ID).email("abc1234@naver,com").nickname("toy").thumbnail("img").build();
        ArticleDTO.Edit request = ArticleDTO.Edit.builder().id(1L).title("제목_수정").body("내용_수정").build();
        Article article = Article.builder().id(1l).member(member).title("제목1").body("내용1").viewCount(0).createDate(LocalDate.now()).sticker(Sticker.NEWHIT).build();
        when(articleRepository.findById(any())).thenReturn(Optional.of(article));



        assertThrows(NoAuthorityExceoption.class, () -> articleService.articleEdit(requestMember,request));
        verify(articleRepository).findById(request.getId());
    }


    @Test
    @DisplayName("게시글 삭제 기능이  제대로 동작하는지 확인")
    void 게시글_삭제_성공_테스트() {
        Long testId = 1L;
        Member member = Member.builder().id(USER_ID).email("chs98412@naver,com").nickname("hyuksoon").thumbnail("img").build();
        Article article = Article.builder().id(1l).member(member).title("제목1").body("내용1").viewCount(0).createDate(LocalDate.now()).sticker(Sticker.NEWHIT).build();
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
        Article article = Article.builder().id(1l).member(member).title("제목1").body("내용1").viewCount(0).createDate(LocalDate.now()).sticker(Sticker.NEWHIT).build();
        when(articleRepository.findById(any())).thenReturn(Optional.of(article));
        assertThrows(NoAuthorityExceoption.class, () -> articleService.articleDelete(requestMember,testId));
        verify(articleRepository).findById(testId);
    }


    @Test
    @DisplayName("게시글 유저별  조회 기능이 제대로 동작하는지 확인")
    void 게시글_유저별조회_성공_테스트() {
        String testEmail = "chs98412@naver,com";
        Member member = Member.builder().id(USER_ID).email(testEmail).nickname("hyuksoon").thumbnail("img").build();
        Article article1 = Article.builder().id(1l).member(member).title("제목1").body("내용1").viewCount(0).createDate(LocalDate.now()).sticker(Sticker.NEWHIT).build();
        Article article2 = Article.builder().id(2l).member(member).title("제목1").body("내용1").viewCount(0).createDate(LocalDate.now()).sticker(Sticker.NEWHIT).build();
        List<Article> articles = new ArrayList<>();
        articles.add(article1);
        articles.add(article2);

        when(memberService.memberFind(any())).thenReturn(member);
        when(articleRepository.findAllByMember(any())).thenReturn(articles);


        List<Article> response = articleService.userArticleRetrieve(testEmail);

        verify(memberService).memberFind(any());
        verify(articleRepository).findAllByMember(any());

    }


    @Test
    @DisplayName("게시글 페이징 조회 기능이 제대로 동작하는지 확인")
    void 게시글_페이징조회_성공_테스트() {
        String testEmail = "chs98412@naver,com";
        Member member = Member.builder().id(USER_ID).email(testEmail).nickname("hyuksoon").thumbnail("img").build();
        List<Article> articles = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Article article = Article.builder().id(1l).member(member).title("제목1").body("내용1").viewCount(0).createDate(LocalDate.now()).sticker(Sticker.NEWHIT).build();

            articles.add(article);
        }

        when(articleRepository.zeroOffsetPaging(any(),any())).thenReturn(articles.subList(3,6));


        List<Article> response = articleService.articleRetrievePagingWithZeroOffset(2l,3);

        verify(articleRepository).zeroOffsetPaging(2l,3);

        System.out.println(response);

        for (int i=3;i<response.size();i++) {
            assertThat(response.get(i).getId()).isEqualTo(articles.get(i).getId());
            assertThat(response.get(i).getTitle()).isEqualTo(articles.get(i).getTitle());
            assertThat(response.get(i).getBody()).isEqualTo(articles.get(i).getBody());

        }


    }

    @Test
    @DisplayName("게시글 조회수 증가 기능이 제대로 동작하는지 확인")
    void 게시글_조회수_증가_성공_테스트() {
        Member member = Member.builder().id(USER_ID).email("chs98412@naver,com").nickname("hyuksoon").thumbnail("img").build();
        Article article = Article.builder().id(1l).member(member).title("제목1").body("내용1").viewCount(0).createDate(LocalDate.now()).sticker(Sticker.NEWHIT).build();

        when(articleRepository.save(any())).thenReturn(article);
        articleService.increaseViewCount(article);

        verify(articleRepository).save(article);
        assertThat(article.getViewCount()).isEqualTo(1);
    }

}