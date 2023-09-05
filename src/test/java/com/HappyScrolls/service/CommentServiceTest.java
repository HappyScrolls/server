package com.HappyScrolls.service;

import com.HappyScrolls.dto.CommentDTO;
import com.HappyScrolls.entity.Article;
import com.HappyScrolls.entity.Comment;
import com.HappyScrolls.entity.Member;
import com.HappyScrolls.exception.NoAuthorityExceoption;
import com.HappyScrolls.repository.CommentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

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
    private ArticleService articleService;
    @Mock
    private CommentRepository commentRepository;
    private static final Long USER_ID = 1L;

    @Test
    @DisplayName("댓글 작성 기능이 제대로 작동하는지 확인")
    void 댓글_작성_성공_테스트() {
        Member member = Member.builder().id(USER_ID).email("chs98412@naver,com").nickname("hyuksoon").thumbnail("img").build();
        Article article = new Article(1l, member, "제목1", "내용1",0, LocalDate.now());
        CommentDTO.ParentRequest request = CommentDTO.ParentRequest.builder().postId(1L).body("댓글 내용").build();
        Comment makeComment = request.toEntity();
        makeComment.setArticle(article);
        makeComment.setMember(member);

        when(articleService.articleRetrieve(any())).thenReturn(article);

        when(commentRepository.save(any())).thenReturn(makeComment);

        Long response= commentService.commentParentCreate(member, request);
        verify(articleService).articleRetrieve(1L);
        verify(commentRepository).save(any());


    }


    @Test
    @DisplayName("댓글 조회 기능이 제대로 동작하는지 확인")
    void 댓글_조회_성공_테스트() {
        Member member = Member.builder().id(USER_ID).email("chs98412@naver,com").nickname("hyuksoon").thumbnail("img").build();
        Article article = new Article(1l, member, "제목1", "내용1",0, LocalDate.now());

        Comment cmt1 = new Comment(1l, member, article, "댓글1",false,null);
        Comment cmt2 = new Comment(2l, member, article, "댓글1",false,null);
        Comment cmt3= new Comment(3l, member, article, "댓글1",false,null);
        List<Comment> comments = new ArrayList<>();
        comments.add(cmt1);
        comments.add(cmt2);
        comments.add(cmt3);



        when(articleService.articleRetrieve(any())).thenReturn(article);

        when(commentRepository.findByArticle(any())).thenReturn(comments);

        List<Comment> response = commentService.commentRetrieve(1L);

        verify(articleService).articleRetrieve(1L);
        verify(commentRepository).findByArticle(article);

//        assertThat(response).isEqualTo(comments.stream()
//                .map(comment -> CommentDTO.ParentResponse.builder()
//                        .id(comment.getId())
//                        .body(comment.getBody())
//                        .build())
//                .collect(Collectors.toList()));
    }



    @Test
    @DisplayName("댓글 수정 기능이  제대로 동작하는지 확인")
    void 댓글_수정_성공_테스트() {
        Member member = Member.builder().id(USER_ID).email("chs98412@naver,com").nickname("hyuksoon").thumbnail("img").build();
        Article article = new Article(1l, member, "제목1", "내용1",0, LocalDate.now());
        Comment cmt = new Comment(1l, member, article, "댓글1",false,null);
        CommentDTO.Edit request = CommentDTO.Edit.builder().id(1l).body("수정 내용").build();


        when(commentRepository.findById(any())).thenReturn(Optional.of(cmt));


        cmt.edit(request);
        when(commentRepository.save(any())).thenReturn(cmt);


        Long response = commentService.commentEdit(member, request);



        verify(commentRepository).findById(1l);
       verify(commentRepository).save(cmt);
//       assertThat(response).isEqualTo(CommentDTO.ParentResponse.builder().id(cmt.getId()).body(cmt.getBody()).build() );

    }

    @Test
    @DisplayName("댓글 수정 기능이 댓을 찾을 수 없는 경우 예외처리를 하는지 확인")
    void 댓글_수정_실패_테스트1() {
        Member member = Member.builder().id(USER_ID).email("chs98412@naver,com").nickname("hyuksoon").thumbnail("img").build();
        CommentDTO.Edit request = CommentDTO.Edit.builder().id(1l).body("수정 내용").build();

        when(commentRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> commentService.commentEdit(member,request));

        verify(commentRepository).findById(1L);
    }

    @Test
    @DisplayName("댓글 수정 기능이 권한 없는 경우 예외처리를 하는지 확인")
    void 댓글_수정_실패_테스트2() {
        Member member = Member.builder().id(USER_ID).email("chs98412@naver,com").nickname("hyuksoon").thumbnail("img").build();
        Member requestMember = Member.builder().id(USER_ID).email("abc1234@naver,com").nickname("toy").thumbnail("img").build();
        Article article = new Article(1l, member, "제목1", "내용1",0, LocalDate.now());
        Comment cmt = new Comment(1l, member, article, "댓글1",false,null);
        CommentDTO.Edit request = CommentDTO.Edit.builder().id(1l).body("수정 내용").build();


        when(commentRepository.findById(any())).thenReturn(Optional.of(cmt));

        assertThrows(NoAuthorityExceoption.class, () -> commentService.commentEdit(requestMember,request));

        verify(commentRepository).findById(1L);
    }

    @Test
    @DisplayName("댓글 삭제 기능이  제대로 동작하는지 확인")
    void 댓글_삭제_성공_테스트() {
        Long testId = 1L;
        Member member = Member.builder().id(USER_ID).email("chs98412@naver,com").nickname("hyuksoon").thumbnail("img").build();
        Article article = new Article(1l, member, "제목1", "내용1",0, LocalDate.now());
        Comment cmt = new Comment(1l, member, article, "댓글1",false,null);

        when(commentRepository.findById(any())).thenReturn(Optional.of(cmt));

        commentService.commentDelete(member, testId);

        verify(commentRepository).findById(testId);
        verify(commentRepository).delete(cmt);
    }

    @Test
    @DisplayName("댓글 삭제 기능이 댓글을 찾을 수 없는 경우 예외처리를 하는지 확인")
    void 댓글_삭제_실패_테스트1() {
        Long testId = 1L;
        Member member = Member.builder().id(USER_ID).email("chs98412@naver,com").nickname("hyuksoon").thumbnail("img").build();
        CommentDTO.Edit request = CommentDTO.Edit.builder().id(testId).body("수정 내용").build();

        when(commentRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> commentService.commentDelete(member,testId));

        verify(commentRepository).findById(1L);
    }

    @Test
    @DisplayName("댓글 삭제 기능이 권한 없는 경우 예외처리를 하는지 확인")
    void 댓글_삭제_실패_테스트2() {
        Long testId = 1L;
        Member member = Member.builder().id(USER_ID).email("chs98412@naver,com").nickname("hyuksoon").thumbnail("img").build();
        Member requestMember = Member.builder().id(USER_ID).email("abc1234@naver,com").nickname("toy").thumbnail("img").build();
        Article article = new Article(1l, member, "제목1", "내용1",0, LocalDate.now());
        Comment cmt= new Comment(1l, member, article, "댓글1",false,null);


        when(commentRepository.findById(any())).thenReturn(Optional.of(cmt));

        assertThrows(NoAuthorityExceoption.class, () -> commentService.commentDelete(requestMember,testId));

        verify(commentRepository).findById(1L);
    }

}