package com.HappyScrolls.service;

import com.HappyScrolls.entity.Article;
import com.HappyScrolls.entity.ViewCount;
import com.HappyScrolls.repository.ViewCountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.View;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class ViewCountService {

    @Autowired
    private ViewCountRepository viewCountRepository;

    @Autowired
    private ArticleService articleService;

    public void viewCountIncrease(Long id) {
        Article article = articleService.articleRetrieve(id);

        LocalDate today = LocalDate.now();


        ViewCount viewCount;
        Optional<ViewCount> optionalViewCount = viewCountRepository.findByCreateDateAndArticle(today,article);
        if (optionalViewCount.isPresent()) {
            viewCount = optionalViewCount.get();
        } else {
            viewCount = ViewCount.builder()
                    .createDate(today)
                    .count(0)
                    .article(article)
                    .build();
        }
        viewCount.increaseViewCount();
        viewCountRepository.save(viewCount);
        articleService.increaseViewCount(article);

    }
}
