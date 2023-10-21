package com.HappyScrolls.domain.product.service;

import com.HappyScrolls.domain.product.adaptor.ProductAdaptor;
import com.HappyScrolls.domain.product.dto.ProductDTO;
import com.HappyScrolls.domain.product.entity.Product;
import com.HappyScrolls.domain.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductAdaptor productAdaptor;

    public Long productCreate(ProductDTO.Request request) {
        return productAdaptor.productCreate(request.toEntity());
    }

    public ProductDTO.Response productRetrieve(Long id) {
        return ProductDTO.Response.toResponseDto(productAdaptor.productRetrieve(id));
    }

    public List<Long> productAllRetrieve() {
        return productAdaptor.productAllRetrieve();
    }


    @Transactional
    public synchronized void test() {
        Product product= productRepository.findById(1l).get();
        if (product.getQuantity() > 0) {
            product.decreaseQuantity();
            productRepository.saveAndFlush(product);
        }
    }
}
