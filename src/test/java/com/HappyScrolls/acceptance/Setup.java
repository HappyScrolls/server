package com.HappyScrolls.acceptance;

import com.HappyScrolls.config.security.JwtTokenUtil;
import com.HappyScrolls.entity.*;
import com.HappyScrolls.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Setup {
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ArticleTagRepository articleTagRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BuyRepository buyRepository;

    @Autowired
    private CartRepository cartRepository;


    Member member;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    public void setdata() {

        //tag
        Tag tag1 = Tag.builder().body("tag1").build();
        Tag tag2 = Tag.builder().body("tag2").build();
        Tag tag3 = Tag.builder().body("tag3").build();
        Tag tag4 = Tag.builder().body("tag4").build();
        tagRepository.save(tag1);
        tagRepository.save(tag2);
        tagRepository.save(tag3);
        tagRepository.save(tag4);

        member = Member.builder().nickname("chs").point(1000).email("chs98412@naver.com").build();
        Member member2 = Member.builder().nickname("2").point(100).email("@naver.com").build();
        Member member3 = Member.builder().nickname("3").point(100).email("@naver.com").build();
        Member member4 = Member.builder().nickname("4").point(100).email("@naver.com").build();
        memberRepository.save(member);
        memberRepository.save(member2);
        memberRepository.save(member3);
        memberRepository.save(member4);


        Article article1 = Article.builder().title("제목1").body("내용1").member(member).viewCount(0).build();
        Article article2 = Article.builder().title("제목2").body("내용2").member(member2).viewCount(0).build();
        Article article3= Article.builder().title("제목3").body("내용3").member(member3).viewCount(0).build();
        Article article4= Article.builder().title("제목4").body("내용4").member(member4).viewCount(0).build();

        articleRepository.save(article1);
        articleRepository.save(article2);
        articleRepository.save(article3);
        articleRepository.save(article4);


        ArticleTag articleTag1 = ArticleTag.builder().article(article1).tag(tag1).build();
        ArticleTag articleTag2 = ArticleTag.builder().article(article2).tag(tag2).build();
        ArticleTag articleTag3 = ArticleTag.builder().article(article3).tag(tag3).build();
        ArticleTag articleTag4 = ArticleTag.builder().article(article4).tag(tag4).build();

        articleTagRepository.save(articleTag1);
        articleTagRepository.save(articleTag2);
        articleTagRepository.save(articleTag3);
        articleTagRepository.save(articleTag4);

        Product product1= Product.builder().price(100).name("product1").quantity(100).build();
        Product product2 = Product.builder().price(100).name("product2").quantity(100).build();
        Product product3 = Product.builder().price(100).name("product3").quantity(100).build();
        Product product4 = Product.builder().price(100).name("product4").quantity(100).build();

        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);
        productRepository.save(product4);


        Cart cart1 = Cart.builder().member(member).product(product1).build();
        Cart cart2= Cart.builder().member(member).product(product2).build();
        Cart cart3 = Cart.builder().member(member).product(product3).build();
        Cart cart4 = Cart.builder().member(member).product(product4).build();

        cartRepository.save(cart1);
        cartRepository.save(cart2);
        cartRepository.save(cart3);
        cartRepository.save(cart4);

        Buy buy1 = Buy.builder().member(member).product(product1).build();
        Buy buy2 = Buy.builder().member(member).product(product2).build();
        Buy buy3 = Buy.builder().member(member).product(product3).build();

        buyRepository.save(buy1);
        buyRepository.save(buy2);
        buyRepository.save(buy3);

    }

    public String tk() {
        return jwtTokenUtil.generateToken("kakao",member.getEmail(),member.getNickname(),"awd");
    }
}