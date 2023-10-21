package com.HappyScrolls.domain.article.entity;

import com.HappyScrolls.domain.article.dto.ArticleDTO;
import com.HappyScrolls.domain.member.entity.Member;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

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


    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;


    private String title;

    private String body;

    private Integer viewCount;

    @JsonSerialize(using= LocalDateSerializer.class)
    @JsonDeserialize(using= LocalDateDeserializer.class)
    private LocalDate createDate;
    @Enumerated(EnumType.STRING)
    private Sticker sticker;
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
