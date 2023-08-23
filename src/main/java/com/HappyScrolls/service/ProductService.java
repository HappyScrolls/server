package com.HappyScrolls.service;

import com.HappyScrolls.dto.ArticleDTO;
import com.HappyScrolls.dto.ProductDTO;
import com.HappyScrolls.entity.Article;
import com.HappyScrolls.entity.Product;
import com.HappyScrolls.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ProductDTO.Response productCreate(ProductDTO.Request request) {
        Product product = request.toEntity();

        productRepository.save(product);


        return ProductDTO.Response.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .expiration(product.getExpiration())
                .build();
    }

    public ProductDTO.Response productRetrieve(Long id) {

        Product product = productRepository.findById(id).orElseThrow(()-> new NoSuchElementException(String.format("product[%s] 상품을 찾을 수 없습니다", id)));


        return ProductDTO.Response.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .expiration(product.getExpiration())
                .build();

    }

    public Product productFind(Long id) {

        Product product = productRepository.findById(id).orElseThrow(()-> new NoSuchElementException(String.format("product[%s] 상품을 찾을 수 없습니다", id)));


        return product;

    }
}
