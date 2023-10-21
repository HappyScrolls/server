package com.HappyScrolls.domain.buy.entity;


import com.HappyScrolls.domain.member.entity.Member;
import com.HappyScrolls.domain.product.entity.Product;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Builder
@Getter
@Setter
@AllArgsConstructor
public class Buy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Product product;
    private LocalDateTime createDate;


}
