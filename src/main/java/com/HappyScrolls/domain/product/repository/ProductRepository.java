package com.HappyScrolls.domain.product.repository;

import com.HappyScrolls.domain.article.entity.Article;
import com.HappyScrolls.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.Repository;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends Repository<Product, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Product> findById(Long id);

    Product save(Product entity);

    List<Product> findAll();

    Product saveAndFlush(Product entity);
}
