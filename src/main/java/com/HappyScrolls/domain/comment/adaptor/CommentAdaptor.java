package com.HappyScrolls.domain.comment.adaptor;

import com.HappyScrolls.config.Adaptor;
import com.HappyScrolls.domain.article.adaptor.ArticleAdaptor;
import com.HappyScrolls.domain.article.entity.Article;
import com.HappyScrolls.domain.comment.dto.CommentDTO;
import com.HappyScrolls.domain.comment.entity.Comment;
import com.HappyScrolls.domain.member.entity.Member;
import com.HappyScrolls.exception.NoAuthorityException;
import com.HappyScrolls.domain.comment.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;
import java.util.NoSuchElementException;

@Adaptor
public class CommentAdaptor {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ArticleAdaptor articleAdaptor;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    public Long commentCreate(Comment request) {
        return commentRepository.save(request).getId();
    }

    public List<Comment> commentRetrieve(Article article) {
        List<Comment> comments = commentRepository.findByArticle(article);
        return comments;
    }

    public Comment commentRetrieveById(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(()-> new NoSuchElementException(String.format("comment[%s] 댓글을 찾을 수 없습니다",id)));
        return comment;
    }


    public Long commentEdit(Member member, CommentDTO.Edit request) {
        Comment comment = commentRetrieveById(request.getId());
        comment.edit(member,request);
        return commentRepository.save(comment).getId();
    }


    public Integer commentDelete(Member member, Long id) {
        Comment comment = commentRetrieveById(id);
        comment.checkPermission(member);
        commentRepository.delete(comment);
        return 1;
    }

}
