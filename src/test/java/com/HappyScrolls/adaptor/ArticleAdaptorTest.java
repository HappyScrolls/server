package com.HappyScrolls.adaptor;

import com.HappyScrolls.domain.article.adaptor.ArticleAdaptor;
import com.HappyScrolls.domain.tag.adaptor.TagAdaptor;
import com.HappyScrolls.domain.article.dto.ArticleDTO;
import com.HappyScrolls.domain.article.entity.Article;
import com.HappyScrolls.domain.member.entity.Member;
import com.HappyScrolls.domain.tag.entity.Tag;
import com.HappyScrolls.exception.NoAuthorityException;
import com.HappyScrolls.exception.NoResultException;
import com.HappyScrolls.domain.article.repository.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ArticleAdaptorTest {

    @InjectMocks
    private ArticleAdaptor articleAdaptor;

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private TagAdaptor tagAdaptor;

    @Test
    @DisplayName("페이징 기반 게시글 조회")
    void 페이징__게시글_조회() {
        Article article = Article.builder().id(1l).build();
        when(articleRepository.zeroOffsetPaging(any(), any())).thenReturn(Arrays.asList(article));

        List<Article> result = articleAdaptor.retrieveByPaging(1L, 10);
        assertEquals(1, result.size());
        assertEquals(1l, result.get(0).getId());
    }

    @Test
    @DisplayName("게시글 조회")
    void retrieveArticleTest() {
        Article article = Article.builder().id(1l).build();
        when(articleRepository.findById(any())).thenReturn(Optional.of(article));

        Article result = articleAdaptor.retrieveArticle(1L);
        assertEquals(article, result);
    }

    @Test
    @DisplayName("게시글 태그 기반 조회")
    void 게시글_태그_기반_조회() {
        Tag tag = new Tag();
        Article article = Article.builder().id(1l).build();
        when(tagAdaptor.tagFind(any())).thenReturn(tag);
        when(articleRepository.findByTagPaging(any(), any())).thenReturn(List.of(article));

        List<Article> result = articleAdaptor.articleRetrieveByTagPaging2(1L, "testTag");
        assertEquals(1, result.size());
        assertEquals(result.get(0).getId(), 1l);
    }

    @Test
    @DisplayName("태그 리스트 기반 게시글 조회")
    void 태그_리스트_기반_조회() {
        Tag tag = new Tag();
        Article article = Article.builder().id(1l).build();
        when(articleRepository.findByTagListPaging(any(), any())).thenReturn(List.of(article));

        List<Article> result = articleAdaptor.articleRetrieveByTagList(1L, List.of(tag));
        assertEquals(1, result.size());
        assertEquals(result.get(0).getId(), 1l);
    }


    @Test
    @DisplayName("게시글 생성")
    void 게시글_생성() {
        Article article = Article.builder().id(1l).build();
        when(articleRepository.save(any())).thenReturn(article);

        Long resultId = articleAdaptor.articleCreate(article);
        assertEquals(article.getId(), resultId);
    }

    @Test
    @DisplayName("게시글 수정")
    void 게시글_수정() {
        Article article = Article.builder().id(1l).build();
        Member member = new Member();
        article.setMember(member);
        when(articleRepository.findById(any())).thenReturn(Optional.of(article));
        when(articleRepository.save(any())).thenReturn(article);

        Long resultId = articleAdaptor.articleEdit(member, new ArticleDTO.Edit());
        assertEquals(article.getId(), resultId);
    }

    @Test
    @DisplayName("게시글 삭제")
    void 게시글_삭제() {
        Article article = Article.builder().id(1l).build();
        Member member = new Member();
        article.setMember(member);
        when(articleRepository.findById(any())).thenReturn(Optional.of(article));
        doNothing().when(articleRepository).delete(any());

        articleAdaptor.articleDelete(member, 1L);
        verify(articleRepository).delete(any());
    }

    @Test
    @DisplayName("사용자 게시글 조회")
    void 사용자_게시글_조회() {
        Article article = Article.builder().id(1l).build();
        Member member = new Member();
        when(articleRepository.findAllByMember(any())).thenReturn(List.of(article));

        List<Article> result = articleAdaptor.userArticleRetrieve(member);
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("검색")
    void 검색() {
        Article article = Article.builder().id(1l).build();
        when(articleRepository.search(any(), any(), any())).thenReturn(List.of(article));

        List<Article> result = articleAdaptor.search(1L, 10, "test");
        assertEquals(1, result.size());
        assertEquals(result.get(0).getId(), 1l);
    }

    @Test
    @DisplayName("페이징 예외처리")
    public void 페이징_빈결과_예외() {
        when(articleRepository.zeroOffsetPaging(any(), any())).thenReturn(new ArrayList<>());

        assertThrows(NoResultException.class, () -> {
            articleAdaptor.retrieveByPaging(1L, 10);
        });
    }

    @Test
    @DisplayName("수정 예외")
    public void 수정_존재하지_않는_예외() {
        when(articleRepository.findById(anyLong())).thenReturn(Optional.empty());

        ArticleDTO.Edit editRequest = new ArticleDTO.Edit(1l,"asd","asd");

        assertThrows(NoSuchElementException.class, () -> {
            articleAdaptor.articleEdit(new Member(), editRequest);
        });
    }

    @Test
    @DisplayName("수정 권한 예외")
    public void 수정_권한_예외() {
        Article article = new Article();
        article.setMember(Member.builder().id(1l).build());

        when(articleRepository.findById(anyLong())).thenReturn(Optional.of(article));

        ArticleDTO.Edit editRequest = new ArticleDTO.Edit(1l,"qwe","qwe");

        assertThrows(NoAuthorityException.class, () -> {
            articleAdaptor.articleEdit(Member.builder().id(2l).build(), editRequest);
        });
    }

    @Test
    @DisplayName("삭제 없는 항목 예외")
    public void 삭제_없는_항목_예외() {
        when(articleRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            articleAdaptor.articleDelete(new Member(), 1L);
        });
    }

    @Test
    @DisplayName("삭제 권한 예외")
    public void 삭제_권한_예외() {
        Article article = new Article();
        article.setMember(Member.builder().id(1l).build());

        when(articleRepository.findById(anyLong())).thenReturn(Optional.of(article));

        assertThrows(NoAuthorityException.class, () -> {
            articleAdaptor.articleDelete(Member.builder().id(2l).build(), 1L);
        });
    }


}

