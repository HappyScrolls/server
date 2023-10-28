package com.HappyScrolls.config.elastic;

import com.HappyScrolls.domain.article.dto.ArticleDTO;
import com.HappyScrolls.domain.article.entity.Sticker;
import com.HappyScrolls.domain.member.entity.Member;
import com.HappyScrolls.exception.NoAuthorityException;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

import javax.persistence.*;
import java.time.LocalDate;

@Builder
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Document(indexName = "articletest")
public class ArticleDoc {

    private Long id;

    private String member_id;


    private String title;

    private String body;

    private Integer viewCount;

    private LocalDate createDate;
    private Sticker sticker;


}
