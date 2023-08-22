package com.HappyScrolls.repository;

import com.HappyScrolls.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
