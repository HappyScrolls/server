package com.HappyScrolls.service;

import com.HappyScrolls.dto.ProductDTO;
import com.HappyScrolls.entity.Product;
import com.HappyScrolls.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product productCreate(ProductDTO.Request request) {
        Product product = request.toEntity();
        productRepository.save(product);
        return product;
    }

    public Product productRetrieve(Long id) {
        Product product = productRepository.findById(id).orElseThrow(()-> new NoSuchElementException(String.format("product[%s] 상품을 찾을 수 없습니다", id)));
        return product;

    }

    public List<Product> productAllRetrieve() {
        List<Product> products =productRepository.findAll();

        return products;
    }
}
