package com.HappyScrolls.controller;

import com.HappyScrolls.config.JwtRequestFilter;
import com.HappyScrolls.config.JwtTokenUtil;
import com.HappyScrolls.dto.ArticleDTO;
import com.HappyScrolls.dto.CommentDTO;
import com.HappyScrolls.entity.Article;
import com.HappyScrolls.entity.Cart;
import com.HappyScrolls.entity.Comment;
import com.HappyScrolls.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ArticleControllerTest extends ControllerTest {

    @SpyBean
    JwtTokenUtil jwtTokenUtil;
    @SpyBean
    JwtRequestFilter jwtRequestFilter;

    private String tk;
    @BeforeEach
    void setup() {
        tk= jwtTokenUtil.generateToken("asd","1","qwe","awdwad");
        System.out.println(tk);

        Member member = Member.builder().nickname("fefefe").email("gmail").thumbnail("Awd").point(123).build();
        when(jwtRequestFilter.getAuthentication(any())).thenReturn(new UsernamePasswordAuthenticationToken(member, "",
                Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))));
    }

    @Test
    void retrieveAllArticlePagewithZeroOffset() throws Exception{

        List<Article> res = new ArrayList<>();


        when(articleService.articleRetrievePagingWithZeroOffset(10l,10)).thenReturn(res);
        ResultActions resultActions = mockMvc.perform(get("/article/zeropaging?lastindex=10&limit=10")  .header("Authorization","Bearer "+tk));

        verify(articleService).articleRetrievePagingWithZeroOffset( 10l,10);

        resultActions.andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void retrieveArticle() throws Exception{

        Article res = new Article();


        when(articleService.articleRetrieve(any())).thenReturn(res);
        ResultActions resultActions = mockMvc.perform(get("/article?id=1")  .header("Authorization","Bearer "+tk));

        verify(articleService).articleRetrieve( any());

        resultActions.andExpect(status().isOk())
                .andDo(print());
    }

//태그, 내글 관련 페이징 후 테스트 작
    @Test
    void createArticle() throws Exception{


        ArticleDTO.Request request = ArticleDTO.Request.builder().build();

        when(articleService.articleCreate(any(Member.class),any( ArticleDTO.Request.class))).thenReturn(1l);


        ResultActions resultActions = mockMvc.perform(
                post("/article")
                        .header("Authorization","Bearer "+tk)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)));


        resultActions.andExpect(status().isOk())
                .andDo(print());

        verify(articleService).articleCreate(any(Member.class),any(ArticleDTO.Request.class));
    }

    @Test
    void editArticle() throws Exception{

        ArticleDTO.Edit request = ArticleDTO.Edit.builder().body("asd").id(1l).build();
        when(articleService.articleEdit(any(Member.class),any(ArticleDTO.Edit.class))).thenReturn(1l);


        ResultActions resultActions = mockMvc.perform(
                put("/article")
                        .header("Authorization","Bearer "+tk)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)));


        resultActions.andExpect(status().isOk())
                .andDo(print());

        verify(articleService).articleEdit(any(Member.class),any(ArticleDTO.Edit.class));
    }


    @Test
    void deleteComment() throws Exception{

        Long request = 1l;
//        (commentService.commentDelete(any(Member.class), any(Long.class))).then();


        ResultActions resultActions = mockMvc.perform(
                delete("/article?id=1")
                        .header("Authorization","Bearer "+tk));


        resultActions.andExpect(status().isOk())
                .andDo(print());

        verify(articleService).articleDelete(any(Member.class),any(Long.class));
    }
}