package com.HappyScrolls;

import com.HappyScrolls.domain.product.entity.Product;
import com.HappyScrolls.domain.product.repository.ProductRepository;
import com.HappyScrolls.domain.product.service.ProductService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class Stocktest {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;

    @Autowired
    private RedisTemplate redisTemplate;
    @BeforeEach
    void before() {
        Product product = Product.builder().id(1l).quantity(100).build();
        productRepository.save(product);
    }

    void test(String thread,int time) {
        redisTemplate.opsForZSet().add(thread,time,1);
    }


    @Test
    void 동시에_100개의_요청() throws InterruptedException {


        int threadCount = 100;

        ExecutorService executorService = Executors.newFixedThreadPool(32);

        CountDownLatch latch = new CountDownLatch(threadCount);


        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                        try {
                            productService.test(1l);

                        }
                        catch (Exception e) {
                            System.out.println("e = " + e);
                        }
                        finally {
                            latch.countDown();
                        }
                    }
            );
        }

        latch.await();

        Product result = productRepository.findById(1L).get();
        System.out.println("result.getQuantity() = " + result.getQuantity());
        assertThat(result.getQuantity()).isEqualTo(0L);

    }
}
