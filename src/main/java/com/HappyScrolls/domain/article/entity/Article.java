package com.HappyScrolls.domain.article.entity;

import com.HappyScrolls.domain.article.dto.ArticleDTO;
import com.HappyScrolls.domain.member.entity.Member;
import com.HappyScrolls.exception.NoAuthorityException;
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

    public void edit(Member requestMember, ArticleDTO.Edit request) {
        if (member.getId() != requestMember.getId()) {
            throw new NoAuthorityException("수정 권한이 없습니다. 본인 소유의 글만 수정 가능합니다.");
        }
        if (request.getTitle() != null) {
            this.title = request.getTitle();
        }
        if (request.getBody() != null) {
            this.body= request.getBody();
        }
    }

    public void checkPermission(Member requestMember) {
        if (member.getId() != requestMember.getId()) {
            throw new NoAuthorityException("삭제 권한이 없습니다. 본인 소유의 글만 삭제  가능합니다.");
        }
    }



}
