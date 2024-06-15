package com.HappyScrolls.domain.product.entity;


import com.HappyScrolls.exception.PointLackException;
import lombok.*;
import org.springframework.data.annotation.Version;

import javax.persistence.*;


@Entity
@Builder
@NoArgsConstructor
@Getter
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private Integer price;

    private Integer quantity;



//    @Version
    private Long version;
    public  void decreaseQuantity() {
        moreThanZero();
        this.quantity -= 1;
    }

    private void moreThanZero() {
        if (quantity <= 0) {
            throw new PointLackException(String.format(" 재고수량이 부족합니다 [%s]", id));
        }
    }

}
