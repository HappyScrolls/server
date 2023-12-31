package com.HappyScrolls.service;

import com.HappyScrolls.dto.ArticleDTO;
import com.HappyScrolls.dto.ProductDTO;
import com.HappyScrolls.entity.Article;
import com.HappyScrolls.entity.Member;
import com.HappyScrolls.entity.Product;
import com.HappyScrolls.repository.ArticleRepository;
import com.HappyScrolls.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;


    @Test
    @DisplayName("상품 생성 기능이 제대로 동작하는지 확인")
    void 상품_작성_성공_테스트() {

        ProductDTO.Request request = ProductDTO.Request.builder().name("제품1").description("내용").price(100).build();

        Product product = request.toEntity();
        when(productRepository.save(any())).thenReturn(product);

        Long response =   productService.productCreate( request);
        verify(productRepository).save(any());

    }

    @Test
    @DisplayName("상품 단건 조회 기능이 제대로 동작하는지 확인")
    void 상품_단건조회_성공_테스트() {

        Product product = Product.builder().price(100).price(100).build();
        when(productRepository.findById(any())).thenReturn(Optional.of(product));


        Product response = productService.productRetrieve(1L);
        verify(productRepository).findById(1L);
        assertThat(response).isEqualTo(product);
    }


    @Test
    @DisplayName("상품 단건 조회 기능이 조회를 할 수 없을 때 예외처리를 하는지 확인")
    void 상품_단건조회_예외_테스트() {
        Long testId = 1L;
        when(productRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> productService.productRetrieve(testId));


        verify(productRepository).findById(testId);
    }

    @Test
    @DisplayName("상품 전체 조회 기능이 제대로 동작하는지 확인")
    void 상품_전체조회_성공_테스트() {

        Product product1 = Product.builder().price(100).price(100).build();
        Product product2 = Product.builder().price(100).price(100).build();
        Product product3 = Product.builder().price(100).price(100).build();


        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);
        products.add(product3);

        when(productRepository.findAll()).thenReturn(products);


        List<Long> response = productService.productAllRetrieve();

        verify(productRepository).findAll();

    }
}