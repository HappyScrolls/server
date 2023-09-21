package com.HappyScrolls.controller;

import com.HappyScrolls.config.JwtTokenUtil;
import com.HappyScrolls.config.OAuth2AuthenticationSuccessHandler;
import com.HappyScrolls.config.UserOAuth2Service;
import com.HappyScrolls.config.WebSecurityConfig;
import com.HappyScrolls.service.*;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProductController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfig.class)
})
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;
    @MockBean
    private MemberService memberService;

    @MockBean
    private TagService tagService;
    @MockBean
    private ArticleService articleService;
    @MockBean
    private CartService cartService;
    @MockBean
    private BuyService buyService;

    @MockBean
    private CommentService commentService;

    @MockBean
    private ViewCountService viewCountService;
    @MockBean
    protected JwtTokenUtil jwtTokenUtil;

    @MockBean
    private UserOAuth2Service userOAuth2Service;

    @MockBean
    private OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    @MockBean
    private ClientRegistrationRepository clientRegistrationRepository;
    @Test
    @WithMockUser
    void test() throws Exception{
        List<Long> res = new ArrayList<>();
        res.add(1l);

        when(productService.productAllRetrieve()).thenReturn(res);

        ResultActions resultActions = mockMvc.perform(
                get("/product/all"));
        resultActions.andExpect(status().isOk())
                .andDo(print());
    }
}