package com.HappyScrolls.service;

import com.HappyScrolls.adaptor.ArticleAdaptor;
import com.HappyScrolls.adaptor.ViewCountAdaptor;
import com.HappyScrolls.entity.Article;
import com.HappyScrolls.entity.ViewCount;
import com.HappyScrolls.exception.UserNotFoundException;
import com.HappyScrolls.repository.ViewCountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.View;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;
import java.util.Optional;

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
