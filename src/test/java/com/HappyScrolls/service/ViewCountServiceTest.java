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
    void test2() {

        Article article = new Article(1l, null, "제목1", "내용1",0, LocalDate.now());
        when(articleService.articleRetrieve(any())).thenReturn(article);
        ViewCount viewCount = ViewCount.builder()
                .createDate(LocalDate.now())
                .count(0)
                .article(article)
                .build();
        when(viewCountRepository.findByCreateDateAndArticle(LocalDate.now(),article)).thenReturn(Optional.of(viewCount));
        when(viewCountRepository.save(any())).thenReturn(viewCount);

        viewCountService.viewCountIncrease(1l);

        verify(viewCountRepository).findByCreateDateAndArticle(LocalDate.now(), article);
        verify(viewCountRepository).save(any());
        verify(articleService).articleRetrieve(1l);
    }
}