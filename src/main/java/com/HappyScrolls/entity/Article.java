package com.HappyScrolls.entity;

import com.HappyScrolls.dto.ArticleDTO;
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
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Member member;

    private String title;

    private String body;

    public void edit(ArticleDTO.Edit request) {
        if (request.getTitle() != null) {
            this.title = request.getTitle();
        }
        if (request.getBody() != null) {
            this.body= request.getBody();
        }
    }
}
