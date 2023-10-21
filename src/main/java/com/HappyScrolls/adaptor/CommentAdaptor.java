package com.HappyScrolls.adaptor;

import com.HappyScrolls.entity.Article;
import com.HappyScrolls.entity.Comment;
import com.HappyScrolls.entity.Member;
import com.HappyScrolls.exception.NoAuthorityException;
import com.HappyScrolls.repository.CommentRepository;
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


    public Long commentEdit(Member member, Comment request) {

        if (!request.getMember().equals(member)) {
            throw new NoAuthorityException("수정 권한이 없습니다. 본인 소유의 글만 수정 가능합니다.");
        }

        return commentRepository.save(request).getId();
    }


    public Integer commentDelete(Member member, Comment request) {

        if (!request.getMember().equals(member)) {
            throw new NoAuthorityException("삭제 권한이 없습니다. 본인 소유의 글만 삭제  가능합니다.");
        }
        commentRepository.delete(request);
        return 1;
    }

}
