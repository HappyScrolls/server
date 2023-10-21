package com.HappyScrolls.adaptor;

import com.HappyScrolls.entity.Article;
import com.HappyScrolls.entity.Comment;
import com.HappyScrolls.entity.Member;
import com.HappyScrolls.exception.NoAuthorityException;
import com.HappyScrolls.repository.CommentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class CommentAdaptorTest {

    @InjectMocks
    private CommentAdaptor commentAdaptor;

    @Mock
    private CommentRepository commentRepository;

    @DisplayName("댓글 생성 테스트")
    @Test
    void 댓글_생성_테스트() {
        Comment comment = Comment.builder().id(1l).build();
        when(commentRepository.save(any())).thenReturn(comment);

        Long result = commentAdaptor.commentCreate(comment);
        assertEquals(comment.getId(), result);
    }

    @DisplayName("댓글 조회 테스트")
    @Test
    void commentRetrieveTest() {
        Article article = Article.builder().id(1l).build();
        Comment comment = Comment.builder().id(1l).article(article).build();

        when(commentRepository.findByArticle(any())).thenReturn(Arrays.asList(comment));

        List<Comment> result = commentAdaptor.commentRetrieve(article);
        assertEquals(1, result.size());
        assertEquals(result.get(0).getArticle().getId(),1l);
        assertEquals(result.get(0).getId(),1l);
    }

    @DisplayName("ID로 댓글 조회 테스트")
    @Test
    void ID로_댓글_조회_테스트() {
        Comment comment = Comment.builder().id(1l).build();
        when(commentRepository.findById(any())).thenReturn(Optional.of(comment));

        Comment result = commentAdaptor.commentRetrieveById(1L);
        assertEquals(comment, result);
    }

    @DisplayName("댓글 수정 ")
    @Test
    void 댓글_수정_성공() {
        Member member = Member.builder().id(1l).email("email").build();

        Comment comment = Comment.builder().id(1l).member(member).build();

        when(commentRepository.save(any())).thenReturn(comment);

        Long result = commentAdaptor.commentEdit(member, comment);
        assertEquals(comment.getId(), result);

    }

    @DisplayName("댓글 수정 실패")
    @Test
    void 댓글_수정_실패() {
        Member member = Member.builder().id(1l).email("email").build();

        Comment comment = Comment.builder().id(1l).member(member).build();

        assertThrows(NoAuthorityException.class, () -> commentAdaptor.commentEdit(new Member(), comment));
    }

    @DisplayName("댓글 삭제 성공 ")
    @Test
    void 댓글_삭제_성공() {
        Member member = Member.builder().id(1l).email("email").build();

        Comment comment = Comment.builder().id(1l).member(member).build();

        doNothing().when(commentRepository).delete(any(Comment.class));

        int result = commentAdaptor.commentDelete(member, comment);
        assertEquals(1, result);

    }

    @DisplayName("댓글 삭제 실패")
    @Test
    void 댓글_삭제_실패() {
        Member member = Member.builder().id(1l).email("email").build();

        Comment comment = Comment.builder().id(1l).member(member).build();
        assertThrows(NoAuthorityException.class, () -> commentAdaptor.commentDelete(new Member(), comment));
    }
}
