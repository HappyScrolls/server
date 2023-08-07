package com.HappyScrolls.entity;

import com.HappyScrolls.dto.CommentDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
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

}
