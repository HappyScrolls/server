package com.HappyScrolls.domain.comment.entity;

import com.HappyScrolls.domain.article.entity.Article;
import com.HappyScrolls.domain.comment.dto.CommentDTO;
import com.HappyScrolls.domain.member.entity.Member;
import com.HappyScrolls.exception.NoAuthorityException;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Article article;

    private String body;

    private Boolean isParent;

    private Long parentId;



    public void edit(Member requestMember, CommentDTO.Edit request) {
        if (member.getId() != requestMember.getId()) {
            throw new NoAuthorityException("수정 권한이 없습니다. 본인 소유의 댓글만 수정 가능합니다.");
        }
        if (request.getBody() != null) {
            this.body= request.getBody();
        }
    }

    public void checkPermission(Member requestMember) {
        if (member.getId() != requestMember.getId()) {
            throw new NoAuthorityException("삭제 권한이 없습니다. 본인 소유의 댓글만 삭제  가능합니다.");
        }
    }




}
