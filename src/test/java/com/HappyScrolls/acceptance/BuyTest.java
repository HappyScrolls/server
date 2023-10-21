//package com.HappyScrolls.acceptance;
//
//import com.HappyScrolls.dto.ArticleDTO;
//import com.HappyScrolls.dto.BuyDTO;
//import com.HappyScrolls.dto.TagDTO;
//import com.HappyScrolls.entity.Article;
//import com.HappyScrolls.repository.ArticleRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.verify;
//import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//public class BuyTest extends BaseIntegrationTest {
//
//
//    private Long id;
//    private String tk="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJ0ZXN0IiwiaWF0IjoxNjkzMTE0MDc3LCJleHAiOjE2OTMxNTAwNzcsInN1YiI6ImNoczk4NDEyQG5hdmVyLmNvbSIsIm5pY2tuYW1lIjoi7LWc7ZiB7IicIiwidWlkIjoiY2hzOTg0MTJAbmF2ZXIuY29tIiwicGxhdGZvcm0iOiJrYWthbyJ9.QdxfLJfNc4ueJ4oIkUB95Cuki8qTP4jV7AKlCqJRxRk";
//
//    @Autowired
//    private Setup setup;
//    @BeforeEach
//    public void setup() {
//        setup.setdata();
//        this.tk = setup.tk();
//    }
//
//    //태그랑 페이징 아직 안함!
//
//    //@Test
//    @Transactional
//    public void 게시글생성() throws Exception {
//        List<Long> cartList = new ArrayList<>();
//        cartList.add(1L);
//        cartList.add(2L);
//        BuyDTO.RequestCart request = BuyDTO.RequestCart.builder().cart(cartList).build();
//        //when
//        ResultActions resultActions = mockMvc.perform(post("/buy")
//                .header("Authorization", "Bearer " + tk)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(request)));
//
//
//        //then
//        resultActions
//                .andExpect(status().isOk());
//
//
//        mockMvc.perform(get("/buy/user")
//                        .header("Authorization", "Bearer " + tk)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andDo(print());
////                .andExpect(jsonPath("$.body").value("awd"));
//    }
//
//
//
//
//   // @Test
//    public void 특정_유저가_작성한_모든_게시글_조회() throws Exception {
//
//        ResultActions resultActions = mockMvc.perform(get("/buy/user")
//                        .header("Authorization","Bearer "+tk)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andDo(print());
//
//
//        //then
////        resultActions
////                .andExpect(status().isOk())
////                .andExpect(jsonPath("$[0].id").value(1))
////                .andDo(print());
//
//    }
//
//
//
//
//
//}
