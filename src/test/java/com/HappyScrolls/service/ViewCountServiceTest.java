package com.HappyScrolls.service;

import com.HappyScrolls.entity.Article;
import com.HappyScrolls.entity.Sticker;
import com.HappyScrolls.entity.ViewCount;
import com.HappyScrolls.repository.ViewCountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class ViewCountServiceTest {

    @InjectMocks
    private ViewCountService viewCountService;
    @Mock
    private ViewCountRepository viewCountRepository;

    @Mock
    private ArticleService articleService;

    @Test
    void name() {

        Article article = new Article(1l, null, "제목1", "내용1",0, LocalDate.now(), Sticker.NEWHIT);
        when(articleService.articleRetrieve(any())).thenReturn(article);
        when(viewCountRepository.findByCreateDateAndArticle(LocalDate.now(),article)).thenReturn(Optional.empty());
        ViewCount viewCount = ViewCount.builder()
                .createDate(LocalDate.now())
                .count(0)
                .article(article)
                .build();
        when(viewCountRepository.save(any())).thenReturn(viewCount);

        viewCountService.viewCountIncrease(1l);

        verify(viewCountRepository).findByCreateDateAndArticle(LocalDate.now(), article);
        verify(viewCountRepository).save(any());
        verify(articleService).articleRetrieve(1l);
    }

    @Test
    void 조회수_증가_성공_테스트() {

        Article article = Article.builder().id(1l).title("제목1").body("내용1").viewCount(0).createDate(LocalDate.now()).sticker(Sticker.NEWHIT).build();
        when(articleService.articleRetrieve(any())).thenReturn(article);
        ViewCount viewCount = ViewCount.builder()
                .createDate(LocalDate.now())
                .count(0)
                .article(article)
                .build();
        when(viewCountRepository.findByCreateDateAndArticle(any(),any())).thenReturn(Optional.of(viewCount));
        when(viewCountRepository.save(any())).thenReturn(viewCount);

        viewCountService.viewCountIncrease(1l);

        verify(viewCountRepository).findByCreateDateAndArticle(LocalDate.now(), article);
        verify(viewCountRepository).save(any());
        verify(articleService).articleRetrieve(1l);
    }

    @Test
    void 조회수_조회_성공() {
        Article article = Article.builder().id(1l).title("제목1").body("내용1").viewCount(10).createDate(LocalDate.now()).sticker(Sticker.NEWHIT).build();
        ViewCount viewCount = ViewCount.builder().count(10).createDate(LocalDate.now()).article(article).build();

        when(articleService.articleRetrieve(1l)).thenReturn(article);
        when(viewCountRepository.findByCreateDateAndArticle(any(), any())).thenReturn(Optional.of(viewCount));

        ViewCount result = viewCountService.retrieveViewCount(1l, LocalDate.now().minusDays(1));

        assertThat(result.getCount()).isEqualTo(10);
    }

    @Test
    void testRetrieveViewcountThrowsException() {
        Long id = 1L; // 테스트하려는 게시글 ID

        Article fakeArticle = new Article();

        when(articleService.articleRetrieve(id)).thenReturn(fakeArticle);

        LocalDate today = LocalDate.now();

        when(viewCountRepository.findByCreateDateAndArticle(today, fakeArticle)).thenReturn(Optional.empty());

        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            viewCountService.retrieveViewCount(id, today);
        });

        String expectedMessage = String.format("date[%s] 날짜에 해당하는 조회수를 찾을 수 없습니다", today);
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}