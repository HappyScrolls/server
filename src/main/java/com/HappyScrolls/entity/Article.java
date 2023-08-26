package com.HappyScrolls.entity;

import com.HappyScrolls.dto.ArticleDTO;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Builder
@Getter
@Setter
@AllArgsConstructor
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Member member;

    private String title;

    private String body;

    private Integer viewCount;

    public void increaseViewCount() {
        this.viewCount += 1;
    }

    public void edit(ArticleDTO.Edit request) {
        if (request.getTitle() != null) {
            this.title = request.getTitle();
        }
        if (request.getBody() != null) {
            this.body= request.getBody();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return Objects.equals(id, article.id) && Objects.equals(member, article.member) && Objects.equals(title, article.title) && Objects.equals(body, article.body);
    }

}
