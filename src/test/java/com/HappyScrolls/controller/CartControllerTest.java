package com.HappyScrolls.controller;

import com.HappyScrolls.config.JwtRequestFilter;
import com.HappyScrolls.config.JwtTokenUtil;
import com.HappyScrolls.dto.CartDTO;
import com.HappyScrolls.dto.CommentDTO;
import com.HappyScrolls.entity.Cart;
import com.HappyScrolls.entity.Comment;
import com.HappyScrolls.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class CartControllerTest extends ControllerTest{


    @SpyBean
    JwtTokenUtil jwtTokenUtil;
    @SpyBean
    JwtRequestFilter jwtRequestFilter;

    private String tk;
    @BeforeEach
    void setup() {
        tk= jwtTokenUtil.generateToken("asd","1","qwe","awdwad");
        System.out.println(tk);

        Member member = Member.builder().nickname("ㅁㅈㅇㅁㅈㅇ").email("gmail").thumbnail("Awd").point(123).build();
        when(jwtRequestFilter.getAuthentication(any())).thenReturn(new UsernamePasswordAuthenticationToken(member, "",
                Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))));
    }

    @Test
    void createCart() throws Exception{


        CartDTO.Request request = CartDTO.Request.builder().productId(1l).build();
        when(cartService.cartCreate(any(Member.class),any( CartDTO.Request.class))).thenReturn(1l);


        ResultActions resultActions = mockMvc.perform(
                post("/cart")
                        .header("Authorization","Bearer "+tk)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)));


        resultActions.andExpect(status().isOk())
                .andDo(print());

        verify(cartService).cartCreate(any(Member.class),any(CartDTO.Request.class));
    }

    @Test
    void retrieveUserCart() throws Exception{

        List<Cart> res = new ArrayList<>();
        res.add(new Cart());

        when(cartService.userCartRetrieve(any())).thenReturn(res);
        ResultActions resultActions = mockMvc.perform(get("/cart")  .header("Authorization","Bearer "+tk));

        verify(cartService).userCartRetrieve( any());

        resultActions.andExpect(status().isOk())
                .andDo(print());
    }

}