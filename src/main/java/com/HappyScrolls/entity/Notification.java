package com.HappyScrolls.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Builder
@Getter
@Setter
@AllArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String category;

    @ManyToOne
    private Member member;

    private Long refId;


    private String msg;
}
