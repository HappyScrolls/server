package com.HappyScrolls.entity;

import com.HappyScrolls.dto.CommentDTO;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

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

    public void edit(CommentDTO.Edit request) {
        if (request.getBody() != null) {
            this.body= request.getBody();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(id, comment.id) && Objects.equals(member, comment.member) && Objects.equals(article, comment.article) && Objects.equals(body, comment.body);
    }


}
