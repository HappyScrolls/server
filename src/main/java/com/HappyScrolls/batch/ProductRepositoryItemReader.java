package com.HappyScrolls.batch;

import com.HappyScrolls.entity.Article;
import com.HappyScrolls.repository.ArticleRepository;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProductRepositoryItemReader extends AbstractPagingItemReader<Article> {

    public ProductRepositoryItemReader(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    private  final ArticleRepository articleRepository;

    private long idx;
    @Override
    protected void doReadPage() {
        if (results == null) {
            results = new ArrayList<>();
        } else {
            results.clear();
        }

        List<Article> products = articleRepository.stickerbatch(LocalDate.now(),getPage());
        System.out.println(products.size());
        System.out.println(getPage());

        results.addAll(products);
    }

    @Override
    protected void doJumpToPage(int itemIndex) {

    }
}
