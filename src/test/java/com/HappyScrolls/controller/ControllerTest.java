package com.HappyScrolls.controller;

import com.HappyScrolls.config.security.OAuth2AuthenticationSuccessHandler;
import com.HappyScrolls.config.security.UserOAuth2Service;
import com.HappyScrolls.domain.article.service.ArticleService;
import com.HappyScrolls.domain.article.service.ViewCountService;
import com.HappyScrolls.domain.buy.service.BuyService;
import com.HappyScrolls.domain.cart.service.CartService;
import com.HappyScrolls.domain.comment.service.CommentService;
import com.HappyScrolls.domain.member.service.MemberService;
import com.HappyScrolls.domain.notification.service.NotificationService;
import com.HappyScrolls.domain.product.service.ProductService;
import com.HappyScrolls.domain.tag.service.TagService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.test.web.servlet.MockMvc;


//@WebMvcTest(controllers = CommentController.class, excludeFilters = {
//        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfig.class)
//})
@WebMvcTest
public class ControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    protected ProductService productService;
    @MockBean
    protected MemberService memberService;

    @MockBean
    protected TagService tagService;
    @MockBean
    protected ArticleService articleService;
    @MockBean
    protected CartService cartService;
    @MockBean
    protected BuyService buyService;

    @MockBean
    protected CommentService commentService;

    @MockBean
    protected ViewCountService viewCountService;

    @MockBean
    protected NotificationService notificationService;


    @MockBean
    protected UserOAuth2Service userOAuth2Service;

    @MockBean
    protected OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    @MockBean
    protected ClientRegistrationRepository clientRegistrationRepository;

    @Autowired
    protected  ObjectMapper objectMapper;


}
