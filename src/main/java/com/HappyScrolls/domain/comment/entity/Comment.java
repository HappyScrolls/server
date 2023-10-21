package com.HappyScrolls.domain.comment.entity;

import com.HappyScrolls.domain.article.entity.Article;
import com.HappyScrolls.domain.comment.dto.CommentDTO;
import com.HappyScrolls.domain.member.entity.Member;
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



    public void edit(CommentDTO.Edit request) {
        if (request.getBody() != null) {
            this.body= request.getBody();
        }
    }




}
