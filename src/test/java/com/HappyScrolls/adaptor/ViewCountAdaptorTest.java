package com.HappyScrolls.adaptor;

import com.HappyScrolls.domain.article.adaptor.ViewCountAdaptor;
import com.HappyScrolls.domain.article.entity.Article;
import com.HappyScrolls.domain.event.ViewCount;
import com.HappyScrolls.domain.article.repository.ViewCountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class ViewCountAdaptorTest {

    @InjectMocks
    private ViewCountAdaptor viewCountAdaptor;

    @Mock
    private ViewCountRepository viewCountRepository;


    @DisplayName("조회수 개수 카운트 테스트")
    @Test
    void 조회수_개수_카운트() {
        LocalDate date = LocalDate.now();
        Article article = new Article();
        when(viewCountRepository.countByCreateDateAndArticle(any(LocalDate.class), any(Article.class))).thenReturn(1L);

        Long result = viewCountAdaptor.count(date, article);
        assertEquals(1L, result);
    }

    @DisplayName("ViewCount 저장 테스트")
    @Test
    void 조회수_저장() {
        ViewCount viewCount =ViewCount.builder().id(1l).build();
        when(viewCountRepository.save(any(ViewCount.class))).thenReturn(viewCount);

        Long result = viewCountAdaptor.saveEntity(viewCount);
        assertEquals(viewCount.getId(), result);
    }

    @DisplayName("특정 날짜와 Article에 해당하는 ViewCount 조회 테스트")
    @Test
    void 조회수_조회_테스트() {
        LocalDate date = LocalDate.now();
        Article article = new Article();
        ViewCount viewCount = new ViewCount();

        when(viewCountRepository.findByCreateDateAndArticle(any(LocalDate.class), any(Article.class))).thenReturn(Optional.of(viewCount));

        ViewCount result = viewCountAdaptor.retrieveViewCount(article, date);
        assertEquals(viewCount, result);
//        assertThrows(NoSuchElementException.class, () -> viewCountAdaptor.retrieveViewCount(article, date));
    }


    @DisplayName("특정 날짜와 Article에 해당하는 ViewCount 조회 실패테스트")
    @Test
    void 조회수_조회_실패() {


        when(viewCountRepository.findByCreateDateAndArticle(any(LocalDate.class), any(Article.class))).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> viewCountAdaptor.retrieveViewCount(new Article(), LocalDate.now()));
    }
}
