package com.HappyScrolls.domain.product.entity;


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
        this.quantity -= 1;
    }


}
