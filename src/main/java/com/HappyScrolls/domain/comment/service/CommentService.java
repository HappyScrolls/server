package com.HappyScrolls.domain.comment.service;

import com.HappyScrolls.domain.article.adaptor.ArticleAdaptor;
import com.HappyScrolls.domain.article.entity.Article;
import com.HappyScrolls.domain.comment.adaptor.CommentAdaptor;
import com.HappyScrolls.domain.comment.entity.Comment;
import com.HappyScrolls.domain.event.CommentEvent;
import com.HappyScrolls.domain.member.entity.Member;
import com.HappyScrolls.domain.comment.dto.CommentDTO;
import com.HappyScrolls.domain.comment.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {


    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ArticleAdaptor articleAdaptor;

    @Autowired
    private CommentAdaptor commentAdaptor;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    public Long commentParentCreate(Member member, CommentDTO.ParentRequest request) {
        Article article = articleAdaptor.retrieveArticle(request.getPostId());
        Comment makeComment = request.toEntity();
        makeComment.setArticle(article);
        makeComment.setMember(member);
        return commentAdaptor.commentCreate(makeComment);
    }
    public Long commentChildCreate(Member member, CommentDTO.ChildRequest request) {
        Comment parentComment = commentAdaptor.commentRetrieveById(request.getParentId());
        Comment makeComment = request.toEntity();
        makeComment.setMember(member);
        makeComment.setArticle(parentComment.getArticle());
        Long response = commentAdaptor.commentCreate(makeComment);

        applicationEventPublisher.publishEvent(new CommentEvent(parentComment,makeComment ));

        return response;
    }



    public List<CommentDTO.Response> commentRetrieve(Long id) {
        Article article = articleAdaptor.retrieveArticle(id);
        return CommentDTO.Response.toCommentResponseDtoList(commentAdaptor.commentRetrieve(article));
    }



    public Long commentEdit(Member member, CommentDTO.Edit request) {

        Comment editComment = commentAdaptor.commentRetrieveById(request.getId());
        editComment.edit(request);
        return commentAdaptor.commentEdit(member,editComment);
    }


    public Integer commentDelete(Member member, Long id) {

        Comment deleteComment = commentAdaptor.commentRetrieveById(id);
        return commentAdaptor.commentDelete(member,deleteComment);
    }



}

