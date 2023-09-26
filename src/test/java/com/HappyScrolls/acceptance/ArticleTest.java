package com.HappyScrolls.acceptance;

import com.HappyScrolls.config.JwtRequestFilter;
import com.HappyScrolls.config.JwtTokenUtil;
import com.HappyScrolls.controller.ControllerTest;
import com.HappyScrolls.dto.ArticleDTO;
import com.HappyScrolls.entity.Article;
import com.HappyScrolls.entity.Member;
import com.HappyScrolls.repository.ArticleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ArticleTest extends BaseIntegrationTest {




    private Long id;
    private String tk="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJ0ZXN0IiwiaWF0IjoxNjkzMTE0MDc3LCJleHAiOjE2OTMxNTAwNzcsInN1YiI6ImNoczk4NDEyQG5hdmVyLmNvbSIsIm5pY2tuYW1lIjoi7LWc7ZiB7IicIiwidWlkIjoiY2hzOTg0MTJAbmF2ZXIuY29tIiwicGxhdGZvcm0iOiJrYWthbyJ9.QdxfLJfNc4ueJ4oIkUB95Cuki8qTP4jV7AKlCqJRxRk";

    @Autowired
private Setup setup;
    @BeforeEach
    public void setup() {
        this.id=setup.saveArticle();
        this.tk = setup.tk();
    }

    @Autowired
    private ArticleRepository articleRepository;


//    @BeforeEach
//    void setup() {
//        tk= jwtTokenUtil.generateToken("asd","1","qwe","awdwad");
//        System.out.println(tk);
//
//        Member member = Member.builder().nickname("ㅁㅈㅇㅁㅈㅇ").email("gmail").thumbnail("Awd").point(123).build();
//        when(jwtRequestFilter.getAuthentication(any())).thenReturn(new UsernamePasswordAuthenticationToken(member, "",
//                Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))));
//    }


//    @Test
//    void retrieveAllArticlePagewithZeroOffset() throws Exception{
//
//        List<Article> res = new ArrayList<>();
//
//
//        when(articleService.articleRetrievePagingWithZeroOffset(10l,10)).thenReturn(res);
//        ResultActions resultActions = mockMvc.perform(get("/article/zeropaging?lastindex=10&limit=10")  .header("Authorization","Bearer "+tk));
//
//        verify(articleService).articleRetrievePagingWithZeroOffset( 10l,10);
//
//        resultActions.andExpect(status().isOk())
//                .andDo(print());
//    }

    @Test
    public void 유저생성() throws Exception {


        //when
        ResultActions resultActions = mockMvc.perform(get("/article?id="+id.toString())
                .header("Authorization","Bearer "+"Awd")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());


        //then
        resultActions
                .andExpect(status().isOk());
    }

//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
    @Test
    public void test() throws Exception {

        ArticleDTO.Request request = ArticleDTO.Request.builder().title("qwer").body("!@3").build();
        //when
        ResultActions resultActions = mockMvc.perform(get("/notification")
                        .header("Authorization","Bearer "+tk)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());


        //then
        resultActions
                .andExpect(status().isOk());


        List<Article> articles = articleRepository.findAll();
        for (Article article : articles) {
            System.out.println(article);
            System.out.println(article.getMember());
        }
    }
}
