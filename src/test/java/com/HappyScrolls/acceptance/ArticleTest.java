package com.HappyScrolls.acceptance;

import com.HappyScrolls.domain.article.dto.ArticleDTO;
import com.HappyScrolls.domain.tag.dto.TagDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(MockRedisConfiguration.class)
public class ArticleTest extends BaseIntegrationTest {



    private Long id;
    private String tk="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJ0ZXN0IiwiaWF0IjoxNjkzMTE0MDc3LCJleHAiOjE2OTMxNTAwNzcsInN1YiI6ImNoczk4NDEyQG5hdmVyLmNvbSIsIm5pY2tuYW1lIjoi7LWc7ZiB7IicIiwidWlkIjoiY2hzOTg0MTJAbmF2ZXIuY29tIiwicGxhdGZvcm0iOiJrYWthbyJ9.QdxfLJfNc4ueJ4oIkUB95Cuki8qTP4jV7AKlCqJRxRk";

    @Autowired
    private Setup setup;
    @BeforeEach
    public void setup() {
        setup.setdata();
        this.tk = setup.tk();
    }

    //태그랑 페이징 아직 안함!

    @Test
    @Transactional
    public void 게시글생성() throws Exception {
        List<TagDTO.Request> tags = new ArrayList<>();
        tags.add(new TagDTO.Request("태그"));
        ArticleDTO.Request request = ArticleDTO.Request.builder().body("새글").tags(tags).build();

        //when
        ResultActions resultActions = mockMvc.perform(post("/article")
                .header("Authorization", "Bearer " + tk)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));


        //then
        resultActions
                .andExpect(status().isOk());


        mockMvc.perform(get("/article?id=5")
                        .header("Authorization","Bearer "+tk)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.id").value(5))
                .andExpect(jsonPath("$.body").value("새글"));
    }


    //@Test
    public void test() throws Exception {

        ArticleDTO.Request request = ArticleDTO.Request.builder().title("qwer").body("!@3").build();
        //when
        ResultActions resultActions = mockMvc.perform(get("/article?id=1")
                        .header("Authorization","Bearer "+tk)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());


        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

    }


    //@Test
    public void 특정_유저가_작성한_모든_게시글_조회() throws Exception {

        ResultActions resultActions = mockMvc.perform(get("/article/user?email=chs98412@naver.com")
                        .header("Authorization","Bearer "+tk)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print());


        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));

    }




    //@Test
    @Transactional
    public void 게시글수정() throws Exception {

        ArticleDTO.Edit request = ArticleDTO.Edit.builder().id(1l).body("change").build();

        //when
        ResultActions resultActions = mockMvc.perform(put("/article")
                .header("Authorization", "Bearer " + tk)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print());


        //then
        resultActions
                .andExpect(status().isOk());


        mockMvc.perform(get("/article?id=1")
                        .header("Authorization","Bearer "+tk)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.body").value("change")).andDo(print());
    }

    //@Test
    @Transactional
    public void 게시글삭제() throws Exception {


        //when
        ResultActions resultActions = mockMvc.perform(delete("/article?id=1")
                        .header("Authorization", "Bearer " + tk));


        //then
        resultActions
                .andExpect(status().isOk());

//??
//        mockMvc.perform(get("/article?id=1")
//                        .header("Authorization","Bearer "+tk)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNotFound())
//                .andDo(print());
    }
}
