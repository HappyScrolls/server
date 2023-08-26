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
@Data
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
