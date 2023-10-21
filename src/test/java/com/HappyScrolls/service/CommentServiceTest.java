package com.HappyScrolls.service;

import com.HappyScrolls.adaptor.ArticleAdaptor;
import com.HappyScrolls.adaptor.CommentAdaptor;
import com.HappyScrolls.dto.CommentDTO;
import com.HappyScrolls.entity.Article;
import com.HappyScrolls.entity.Comment;
import com.HappyScrolls.entity.Member;
import com.HappyScrolls.repository.CommentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @InjectMocks
    private CommentService commentService;

    @Mock
    private CommentRepository commentRepository;
    @Mock
    private ArticleAdaptor articleAdaptor;
    @Mock
    private CommentAdaptor commentAdaptor;
    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @Test
    @DisplayName("부모 댓글 생성")
    public void 부모댓글생성() {
        CommentDTO.ParentRequest request = new CommentDTO.ParentRequest();
        when(articleAdaptor.retrieveArticle(any())).thenReturn(new Article());
        when(commentAdaptor.commentCreate(any())).thenReturn(1L);

        Long result = commentService.commentParentCreate(new Member(), request);
        assertEquals(1L, result);
    }

    @Test
    @DisplayName("자식 댓글 생성")
    public void 자식댓글생성() {
        CommentDTO.ChildRequest request = new CommentDTO.ChildRequest();
        when(commentAdaptor.commentRetrieveById(any())).thenReturn(new Comment());
        when(commentAdaptor.commentCreate(any())).thenReturn(1L);

        Long result = commentService.commentChildCreate(new Member(), request);

        assertEquals(1L, result);
    }

    @Test
    @DisplayName("댓글 조회")
    public void 댓글조회() {
        when(articleAdaptor.retrieveArticle(any())).thenReturn(new Article());
        when(commentAdaptor.commentRetrieve(any())).thenReturn(List.of(new Comment()));

        List<CommentDTO.Response> results = commentService.commentRetrieve(1L);
        assertEquals(1, results.size());
    }



    @Test
    @DisplayName("댓글 수정")
    public void 댓글수정() {
        CommentDTO.Edit request = new CommentDTO.Edit(1L, "test");
        when(commentAdaptor.commentRetrieveById(any())).thenReturn(new Comment());
        when(commentAdaptor.commentEdit(any(), any())).thenReturn(1L);

        Long result = commentService.commentEdit(new Member(), request);

        assertEquals(1L, result);
    }

    @Test
    @DisplayName("댓글 삭제")
    public void 댓글삭제() {
        when(commentAdaptor.commentRetrieveById(any())).thenReturn(new Comment());
        when(commentAdaptor.commentDelete(any(), any())).thenReturn(1);

        Integer result = commentService.commentDelete(new Member(), 1L);

        assertEquals(1, result);
    }
}