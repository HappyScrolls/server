package com.HappyScrolls.service;

import com.HappyScrolls.dto.ArticleDTO;
import com.HappyScrolls.entity.Article;
import com.HappyScrolls.entity.Member;
import com.HappyScrolls.repository.ArticleRepository;
import com.HappyScrolls.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayWithSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ArticleServiceTest {

    @InjectMocks
    private ArticleService articleService;

    @Mock
    private ArticleRepository articleRepository;

    @Test
    @DisplayName("게시물 작성 테스트")
    void 게시물_작성_성공_테스트() {
//            Member member = Member.builder().build();
//            Article article = Article.builder()
//                    .member(member)
//                    .title("test")
//                    .body("testBody")
//                    .build();
//
//        ArticleDTO.Request dto = ArticleDTO.Request.builder().title(article.getTitle()).body(article.getBody()).build();
//
//
//        articleService.articleCreate(member, dto);


    }
}