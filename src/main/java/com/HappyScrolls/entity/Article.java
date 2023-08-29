package com.HappyScrolls.entity;

import com.HappyScrolls.dto.ArticleDTO;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
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


    private Long memberId;

    private String title;

    private String body;

    private Integer viewCount;

    private LocalDate createDate;

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



}
