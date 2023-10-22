package com.HappyScrolls.adaptor;

import com.HappyScrolls.dto.ProductDTO;
import com.HappyScrolls.entity.Product;
import com.HappyScrolls.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Adaptor
public class ProductAdaptor {


    @Autowired
    private ProductRepository productRepository;

    public Long productCreate(Product request) {
        return productRepository.save(request).getId();
    }

    public Product productRetrieve(Long id) {
        return productRepository.findById(id).orElseThrow(()-> new NoSuchElementException(String.format("product[%s] 상품을 찾을 수 없습니다", id)));
    }

    public List<Long> productAllRetrieve() {
        return productRepository.findAll().stream().map(product -> product.getId()).collect(Collectors.toList());
    }

}
