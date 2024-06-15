package com.HappyScrolls.domain.article.adaptor;

import com.HappyScrolls.config.Adaptor;
import com.HappyScrolls.domain.article.entity.Article;
import com.HappyScrolls.domain.event.ViewCount;
import com.HappyScrolls.domain.article.repository.ViewCountRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.NoSuchElementException;

@Adaptor
public class ViewCountAdaptor {

    @Autowired
    private ViewCountRepository viewCountRepository;


    public Long count(LocalDate date, Article article) {
        return viewCountRepository.countByCreateDateAndArticle(date, article);
    }

    public Long saveEntity(ViewCount request) {
        return viewCountRepository.save(request).getId();
    }

    public ViewCount retrieveViewCount(Article article,LocalDate date) {
        return viewCountRepository.findByCreateDateAndArticle(date,article).orElseThrow((() -> new NoSuchElementException(String.format("date[%s] 날짜에 해당하는 조회수를 찾을 수 없습니다", date))));

    }
}
