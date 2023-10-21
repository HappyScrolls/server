package com.HappyScrolls.controller;

import com.HappyScrolls.config.security.WebSecurityConfig;
import com.HappyScrolls.domain.product.controller.ProductController;
import com.HappyScrolls.domain.product.dto.ProductDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProductController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfig.class)
})
class ProductControllerTest extends ControllerTest{

    @Test
    @WithMockUser
    void createParentComment() throws Exception{
        List<Long> res = new ArrayList<>();
        res.add(1l);

        when(productService.productAllRetrieve()).thenReturn(res);

        ResultActions resultActions = mockMvc.perform(
                get("/product/all"));


        resultActions.andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @WithMockUser

    void retrieveProduct() throws Exception{
        ProductDTO.Response response = new ProductDTO.Response();

        when(productService.productRetrieve(1l)).thenReturn(response);

        ResultActions resultActions = mockMvc.perform(
                get("/product?id=1"));


        resultActions.andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @WithMockUser
    void retrieveAllProductProduct() throws Exception{
        List<Long> response=new ArrayList<>();

        when(productService.productAllRetrieve()).thenReturn(response);

        ResultActions resultActions = mockMvc.perform(
                get("/product/all"));


        resultActions.andExpect(status().isOk())
                .andDo(print());
    }

}