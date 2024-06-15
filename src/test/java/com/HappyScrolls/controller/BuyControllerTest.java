package com.HappyScrolls.controller;

import com.HappyScrolls.config.security.JwtRequestFilter;
import com.HappyScrolls.config.security.JwtTokenUtil;
import com.HappyScrolls.domain.buy.dto.BuyDTO;
import com.HappyScrolls.domain.member.entity.Member;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class BuyControllerTest extends ControllerTest{
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
    void createBuy() throws Exception{

        List<Long> res = new ArrayList<>();

        BuyDTO.RequestCart request = BuyDTO.RequestCart.builder().build();
        when(buyService.buyCreate(any(Member.class),any( BuyDTO.RequestCart.class))).thenReturn(res);


        ResultActions resultActions = mockMvc.perform(
                post("/buy")
                        .header("Authorization","Bearer "+tk)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)));


        resultActions.andExpect(status().isOk())
                .andDo(print());

        verify(buyService).buyCreate(any(Member.class),any(BuyDTO.RequestCart.class));
    }


    //안됨 ㅜㅠ
//    @Test
//    void retrieveUserBuy() throws Exception{
//
//        List<Buy> res = new ArrayList<>();
//        res.add(new Buy());
//
//        when(buyService.buyRetrieveUser(any())).thenReturn(res);
//
//        ResultActions resultActions = mockMvc.perform(get("/buy/user")  .header("Authorization","Bearer "+tk));
//
//        verify(buyService).buyRetrieveUser( any());
//
//        resultActions.andExpect(status().isOk())
//                .andDo(print());
//    }

}