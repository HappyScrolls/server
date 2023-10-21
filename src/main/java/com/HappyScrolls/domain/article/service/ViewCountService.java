package com.HappyScrolls.domain.article.service;

import com.HappyScrolls.domain.article.adaptor.ArticleAdaptor;
import com.HappyScrolls.domain.article.adaptor.ViewCountAdaptor;
import com.HappyScrolls.domain.article.entity.Article;
import com.HappyScrolls.domain.event.ViewCount;
import com.HappyScrolls.domain.article.repository.ViewCountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class ViewCountService {

    @Autowired
    private ViewCountRepository viewCountRepository;

    @Autowired
    private ArticleAdaptor articleAdaptor;

    @Autowired
    private ViewCountAdaptor viewCountAdaptor;

    @Transactional
    public void viewCountIncrease(Long id) {
        Article article = articleAdaptor.retrieveArticle(id);

        LocalDate today = LocalDate.now();


        ViewCount viewCount;
        if (viewCountAdaptor.count(today,article).equals(1l)) {
            viewCount = viewCountAdaptor.retrieveViewCount(article, today);
        } else {
            viewCount = ViewCount.builder()
                    .createDate(today)
                    .count(0)
                    .article(article)
                    .build();
        }
        viewCount.increaseViewCount();
        viewCountAdaptor.saveEntity(viewCount);
        article.increaseViewCount(); //???

    }

    public ViewCount retrieveViewCount(Long id,LocalDate date) {
        Article article = articleAdaptor.retrieveArticle(id);

        return viewCountAdaptor.retrieveViewCount(article, LocalDate.now());

    }
}
