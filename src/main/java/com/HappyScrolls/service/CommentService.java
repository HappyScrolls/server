package com.HappyScrolls.service;

import com.HappyScrolls.dto.CommentDTO;
import com.HappyScrolls.entity.Article;
import com.HappyScrolls.entity.Comment;
import com.HappyScrolls.entity.Member;
import com.HappyScrolls.exception.NoAuthorityExceoption;
import com.HappyScrolls.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CommentService {


    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ArticleService articleService;

    public CommentDTO.ParentResponse commentCreate(Member member, CommentDTO.ParentRequest request) {

        Article article = articleService.articleFind(request.getPostId());


        Comment makeComment = request.toEntity();
        makeComment.setArticle(article);
        makeComment.setMember(member);

        Comment comment=commentRepository.save(makeComment);

        return CommentDTO.ParentResponse
                .builder()
                .id(comment.getId())
                .body(comment.getBody())
                .build();
    }

    public List<CommentDTO.ParentResponse> commentRetrieve(Long id) {
        Article article = articleService.articleFind(id);

        List<Comment> comments = commentRepository.findByArticle(article);

        List<CommentDTO.ParentResponse> response = new ArrayList<>();
        for (Comment comment : comments) {
            response.add(CommentDTO.ParentResponse
                    .builder()
                    .id(comment.getId())
                    .body(comment.getBody())
                    .build());
        }

        return response;

    }


    public CommentDTO.ParentResponse commentEdit(Member member, CommentDTO.Edit request) {


        Comment editComment = commentRepository.findById(request.getId()).orElseThrow(() -> new NoSuchElementException(String.format("comment[%s] 댓글을 찾을 수 없습니다",request.getId())));

        if (!editComment.getMember().equals(member)) {
            throw new NoAuthorityExceoption("수정 권한이 없습니다. 본인 소유의 글만 수정 가능합니다.");
        }

        editComment.edit(request);

        Comment comment = commentRepository.save(editComment);

        return CommentDTO.ParentResponse
                .builder()
                .id(comment.getId())
                .body(comment.getBody())
                .build();
    }


    public void commentDelete(Member member, Long id) {

        Comment deleteComment = commentRepository.findById(id).orElseThrow(() -> new NoSuchElementException(String.format("comment[%s] 댓글을 찾을 수 없습니다",id)));


        if (!deleteComment.getMember().equals(member)) {
            throw new NoAuthorityExceoption("삭제 권한이 없습니다. 본인 소유의 글만 삭제  가능합니다.");
        }

        commentRepository.delete(deleteComment);

    }
}

