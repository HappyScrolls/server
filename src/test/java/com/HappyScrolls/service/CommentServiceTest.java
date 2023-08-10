package com.HappyScrolls.service;

import com.HappyScrolls.dto.CommentDTO;
import com.HappyScrolls.entity.Article;
import com.HappyScrolls.entity.Comment;
import com.HappyScrolls.entity.Member;
import com.HappyScrolls.repository.ArticleRepository;
import com.HappyScrolls.repository.CommentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

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
    private ArticleRepository articleRepository;
    @Mock
    private CommentRepository commentRepository;
    private static final Long USER_ID = 1L;

    @Test
    @DisplayName("댓글 작성 기능이 제대로 작동하는지 확인")
    void 댓글_작성_성공_테스트() {
        Member member = Member.builder().id(USER_ID).email("chs98412@naver,com").nickname("hyuksoon").thumbnail("img").build();
        Article article = new Article(1l, member, "제목1", "내용1");
        CommentDTO.Request request = CommentDTO.Request.builder().postId(1L).body("댓글 내용").build();
        Comment makeComment = request.toEntity();
        makeComment.setArticle(article);
        makeComment.setMember(member);

        when(articleRepository.findById(any())).thenReturn(Optional.of(article));

        when(commentRepository.save(any())).thenReturn(makeComment);

        CommentDTO.Response response= commentService.commentCreate(member, request);
        verify(articleRepository).findById(1L);
        verify(commentRepository).save(makeComment);
        assertThat(response).isEqualTo(CommentDTO.Response
                .builder()
                .id(makeComment.getId())
                .body(makeComment.getBody())
                .build());

    }

    @Test
    @DisplayName("댓글 작성 기능이 게시글을 찾을 수 없는 경우 예외처리를 하는지 확인")
    void 댓글_작성_실패_테스트() {
        Member member = Member.builder().id(USER_ID).email("chs98412@naver,com").nickname("hyuksoon").thumbnail("img").build();
        CommentDTO.Request request = CommentDTO.Request.builder().postId(1L).body("댓글 내용").build();
        when(articleRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> commentService.commentCreate(member,request));

        verify(articleRepository).findById(1L);
    }


}