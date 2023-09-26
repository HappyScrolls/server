package com.HappyScrolls.service;

import com.HappyScrolls.dto.BuyDTO;
import com.HappyScrolls.entity.Buy;
import com.HappyScrolls.entity.Cart;
import com.HappyScrolls.entity.Member;
import com.HappyScrolls.entity.Product;
import com.HappyScrolls.exception.PointLackException;
import com.HappyScrolls.repository.BuyRepository;
import com.HappyScrolls.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class DBTest {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;

    @BeforeEach
    void setup() {
        Product product1 = Product.builder().quantity(100).build();
        productRepository.save(product1);
    }

    @Transactional
    void serviceMethod() {
        Product product= productRepository.findById(1l).get();
        if (product.getQuantity() > 0) {
            product.decreaseQuantity();
            System.out.println(product.getVersion());
            productRepository.saveAndFlush(product);
        }
    }
    @Test
    void  정합성_테스트() throws InterruptedException{

        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    productService.test();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                } finally {
                    latch.countDown();
                }
                    }
            );
        }
        latch.await();
        Product product= productRepository.findById(1l).get();
        System.out.println("Product Quantity: "+product.getQuantity());

        assertThat(product.getQuantity()).isEqualTo(0);
    }



}
