package com.HappyScrolls.adaptor;

import com.HappyScrolls.entity.Article;
import com.HappyScrolls.entity.ViewCount;
import com.HappyScrolls.repository.ViewCountRepository;
import com.HappyScrolls.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

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
