package com.HappyScrolls.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.HappyScrolls.dto.ArticleDTO;
import com.HappyScrolls.entity.Article;
import com.HappyScrolls.entity.Member;
import com.HappyScrolls.repository.ArticleRepository;
import com.HappyScrolls.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
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
import static org.mockito.BDDMockito.given;

@SpringBootTest
class ArticleServiceTest {

    @InjectMocks
    private ArticleService articleService;

    @Mock
    private ArticleRepository articleRepository;



    private static final Long ARTICLE_ID = 1L;
    private static final Long USER_ID = 1L;

    private Member member = Member.builder().id(USER_ID).email("chs98412@naver,com").nickname("hyuksoon").thumbnail("img").build();
    private ArticleDTO.Request request = ArticleDTO.Request.builder().title("제목").body("내용").build();
    private Article article = request.toEntity();


    @BeforeEach
    void init() {
        article.setMember(member);
    }

    @Test
    @DisplayName("게시물 작성 테스트")
    void 게시물_작성_성공_테스트() {

        given(articleRepository.save(any())).willReturn(article);



        articleService.articleCreate(member, request);

        verify(articleRepository).save(article);


    }
}