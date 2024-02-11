package com.HappyScrolls.domain.product.service;

import com.HappyScrolls.domain.product.adaptor.ProductAdaptor;
import com.HappyScrolls.domain.product.dto.ProductDTO;
import com.HappyScrolls.domain.product.entity.Product;
import com.HappyScrolls.domain.product.repository.ProductRepository;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductAdaptor productAdaptor;

    @Autowired
    private RedissonClient redissonClient;

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
    public void test(Long id) {
        RLock lock = redissonClient.getLock(id.toString());

            try {
                lock.tryLock(10, 1, TimeUnit.SECONDS);

            Product product= productRepository.findById(1l).get();
            if (product.getQuantity() > 0) {
                product.decreaseQuantity();
                productRepository.saveAndFlush(product);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }
    @Transactional
    public void test2() {
        productRepository.test(1l);
    }
}
