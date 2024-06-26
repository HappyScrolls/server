package com.HappyScrolls.controller;

import com.HappyScrolls.config.security.JwtRequestFilter;
import com.HappyScrolls.config.security.JwtTokenUtil;
import com.HappyScrolls.domain.comment.dto.CommentDTO;
import com.HappyScrolls.domain.member.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.ResultActions;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CommentControllerTest extends ControllerTest {


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
    void createParentComment() throws Exception{


        CommentDTO.ParentRequest request = CommentDTO.ParentRequest.builder().isParent(true).body("asd").postId(1l).build();
        when(commentService.commentParentCreate(any(Member.class),any( CommentDTO.ParentRequest.class))).thenReturn(1l);


        ResultActions resultActions = mockMvc.perform(
                post("/comment")
                        .header("Authorization","Bearer "+tk)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)));


        resultActions.andExpect(status().isOk())
                .andDo(print());

       verify(commentService).commentParentCreate(any(Member.class),any(CommentDTO.ParentRequest.class));
    }

    @Test
    void createChildComment() throws Exception{

        CommentDTO.ChildRequest request = CommentDTO.ChildRequest.builder().isParent(false).body("asd").parentId(1l).build();
        when(commentService.commentChildCreate(any(Member.class),any(CommentDTO.ChildRequest.class))).thenReturn(1l);


        ResultActions resultActions = mockMvc.perform(
                post("/comment/child")
                        .header("Authorization","Bearer "+tk)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)));


        resultActions.andExpect(status().isOk())
                .andDo(print());

        verify(commentService).commentChildCreate(any(Member.class),any(CommentDTO.ChildRequest.class));
    }
    @Test
    void retrieveParentComment() throws Exception{

        List<CommentDTO.Response> res = new ArrayList<>();
        res.add(new CommentDTO.Response());

        when(commentService.commentRetrieve(any())).thenReturn(res);
        ResultActions resultActions = mockMvc.perform(get("/comment?id=1")  .header("Authorization","Bearer "+tk));

        verify(commentService).commentRetrieve( any());

        resultActions.andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void editComment() throws Exception{

        CommentDTO.Edit request = CommentDTO.Edit.builder().body("asd").id(1l).build();
        when(commentService.commentEdit(any(Member.class),any(CommentDTO.Edit.class))).thenReturn(1l);


        ResultActions resultActions = mockMvc.perform(
                put("/comment")
                        .header("Authorization","Bearer "+tk)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)));


        resultActions.andExpect(status().isOk())
                .andDo(print());

        verify(commentService).commentEdit(any(Member.class),any(CommentDTO.Edit.class));
    }


    @Test
    void deleteComment() throws Exception{

        Long request = 1l;
//        (commentService.commentDelete(any(Member.class), any(Long.class))).then();


        ResultActions resultActions = mockMvc.perform(
                delete("/comment?id=1")
                        .header("Authorization","Bearer "+tk));


        resultActions.andExpect(status().isOk())
                .andDo(print());

        verify(commentService).commentDelete(any(Member.class),any(Long.class));
    }
}