package com.HappyScrolls.config.elastic;

import java.util.List;

public interface ArticleDocCustomRepository {
    List<ArticleDoc> find(String parameter);
}
