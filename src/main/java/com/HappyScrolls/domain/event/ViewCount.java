package com.HappyScrolls.domain.event;

import com.HappyScrolls.domain.article.entity.Article;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Builder
@Getter
@Setter
@AllArgsConstructor
public class ViewCount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate createDate;

    @ManyToOne
    private Article article;

    private Integer count;

    public void increaseViewCount() {
        this.count += 1;
    }
}
