package com.HappyScrolls.service;

import com.HappyScrolls.adaptor.ProductAdaptor;
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
import java.util.*;

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
    private ProductAdaptor productAdaptor;

    @Test
    @DisplayName("상품 생성")
    public void 상품생성() {
        ProductDTO.Request request = ProductDTO.Request.builder()
                .name("name")
                .description("description")
                .price(100)
                .quantity(10)
                .build();
        when(productAdaptor.productCreate(any())).thenReturn(1L);

        Long result = productService.productCreate(request);

        assertEquals(1L, result);
    }

    @Test
    @DisplayName("상품 조회")
    public void 상품조회() {
        when(productAdaptor.productRetrieve(any())).thenReturn(new Product());

        ProductDTO.Response result = productService.productRetrieve(1L);

        assertEquals(ProductDTO.Response.class, result.getClass());
    }

    @Test
    @DisplayName("모든 상품 아이디 조회")
    public void 모든상품아이디조회() {
        when(productAdaptor.productAllRetrieve()).thenReturn(Collections.singletonList(1L));

        List<Long> results = productService.productAllRetrieve();

        assertEquals(1, results.size());
    }


}