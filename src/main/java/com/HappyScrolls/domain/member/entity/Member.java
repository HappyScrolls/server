package com.HappyScrolls.domain.member.entity;

import com.HappyScrolls.exception.PointLackException;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Builder
@NoArgsConstructor
@Getter
@AllArgsConstructor
@Setter
public class Member implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String nickname;

    private String thumbnail;


    private Integer point;

    public void decreasePoint(int requirePoints) {
        if(point<requirePoints)  throw new PointLackException(String.format("포인트가 부족합니다 보유 포인트 :[%s] 필요 포인트 : [%s] 부족한 포인트 : [%s]",  point,requirePoints,requirePoints- point));

        this.point -= requirePoints;
    }

    public void increasePoint(int point) {
        this.point += point;
    }

}
