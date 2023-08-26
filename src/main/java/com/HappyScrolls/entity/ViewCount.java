package com.HappyScrolls.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
