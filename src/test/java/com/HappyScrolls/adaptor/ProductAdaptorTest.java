package com.HappyScrolls.adaptor;

import com.HappyScrolls.entity.Product;
import com.HappyScrolls.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
public class ProductAdaptorTest {

    @InjectMocks
    private ProductAdaptor productAdaptor;

    @Mock
    private ProductRepository productRepository;



    @Test
    @DisplayName("상품 생성 테스트")
    public void 상품_생성_테스트() {
        Product product = Product.builder().id(1l).build();

        when(productRepository.save(any())).thenReturn(product);

        Long resultId = productAdaptor.productCreate(product);
        assertEquals(1L, resultId);
    }

    @Test
    @DisplayName("상품 조회 테스트")
    public void 상품_조회_테스트() {
        Product product = Product.builder().id(1l).build();


        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Product resultProduct = productAdaptor.productRetrieve(1L);
        assertEquals(1L, resultProduct.getId());
    }

    @Test
    @DisplayName("상품 조회 실패 테스트")
    public void 상품_조회_실패() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            productAdaptor.productRetrieve(1L);
        });
    }

    @Test
    @DisplayName("모든 상품 조회 테스트")
    public void 상품_모두조회() {
        Product product1 = Product.builder().id(1l).build();

        Product product2 = Product.builder().id(2l).build();

        List<Product> productList = List.of(product1, product2);

        when(productRepository.findAll()).thenReturn(productList);

        List<Long> resultIds = productAdaptor.productAllRetrieve();
        assertEquals(2, resultIds.size());
        assertTrue(resultIds.contains(1L));
        assertTrue(resultIds.contains(2L));
        assertEquals(resultIds,List.of(1l,2l));
    }
}
