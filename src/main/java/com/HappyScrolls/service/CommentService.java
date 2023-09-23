package com.HappyScrolls.service;

import com.HappyScrolls.dto.CommentDTO;
import com.HappyScrolls.entity.*;
import com.HappyScrolls.exception.NoAuthorityExceoption;
import com.HappyScrolls.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
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

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    public Long commentParentCreate(Member member, CommentDTO.ParentRequest request) {
        Article article = articleService.articleRetrieve(request.getPostId());
        Comment makeComment = request.toEntity();
        makeComment.setArticle(article);
        makeComment.setMember(member);
        commentRepository.save(makeComment);
        return makeComment.getId();
    }
    public Long commentChildCreate(Member member, CommentDTO.ChildRequest request) {
        Comment parentComment = commentRetrieveById(request.getParentId());
        Comment makeComment = request.toEntity();
        makeComment.setMember(member);
        makeComment.setArticle(parentComment.getArticle());
        commentRepository.save(makeComment);


        applicationEventPublisher.publishEvent(new CommentEvent("test",parentComment.getMember().getEmail(), member.getEmail(), makeComment.getId(),"댓글생성이벤트" ));//어떻게테스트??



        return makeComment.getId();
    }



    public List<Comment> commentRetrieve(Long id) {
        Article article = articleService.articleRetrieve(id);
        List<Comment> comments = commentRepository.findByArticle(article);
        return comments;
    }

    public Comment commentRetrieveById(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(()-> new NoSuchElementException(String.format("comment[%s] 댓글을 찾을 수 없습니다",id)));
        return comment;
    }


    public Long commentEdit(Member member, CommentDTO.Edit request) {

        Comment editComment = commentRepository.findById(request.getId()).orElseThrow(() -> new NoSuchElementException(String.format("comment[%s] 댓글을 찾을 수 없습니다",request.getId())));
        if (!editComment.getMember().equals(member)) {
            throw new NoAuthorityExceoption("수정 권한이 없습니다. 본인 소유의 글만 수정 가능합니다.");
        }
        editComment.edit(request);
        commentRepository.save(editComment);
        return editComment.getId();
    }


    public void commentDelete(Member member, Long id) {

        Comment deleteComment = commentRepository.findById(id).orElseThrow(() -> new NoSuchElementException(String.format("comment[%s] 댓글을 찾을 수 없습니다",id)));
        if (!deleteComment.getMember().equals(member)) {
            throw new NoAuthorityExceoption("삭제 권한이 없습니다. 본인 소유의 글만 삭제  가능합니다.");
        }
        commentRepository.delete(deleteComment);
    }



}

