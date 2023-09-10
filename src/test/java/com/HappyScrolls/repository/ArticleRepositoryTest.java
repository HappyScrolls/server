package com.HappyScrolls.repository;

import com.HappyScrolls.TestConfig;
import com.HappyScrolls.dto.ArticleDTO;
import com.HappyScrolls.entity.Article;
import com.HappyScrolls.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@DataJpaTest
@ActiveProfiles("test")
@Import(TestConfig.class)
public class ArticleRepositoryTest {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private MemberRepository memberRepository;
    private static final Long USER_ID = 1L;


    @Test
    @Transactional
    @DisplayName("게시글 DB에 저장이 잘 되는지 확인")
    void 게시글_저장_성공_테스트() {

       Member member = Member.builder().email("chs98412@naver,com").nickname("hyuksoon").thumbnail("img").build();
        memberRepository.save(member);
        ArticleDTO.Request request = ArticleDTO.Request.builder().title("제목").body("내용").build();

        Article article = request.toEntity();
        article.setMember(member);

        Article savedArticle = articleRepository.save(article);
        assertThat(savedArticle.getId()).isEqualTo(article.getId());
        assertThat(savedArticle.getTitle()).isEqualTo(article.getTitle());
        assertThat(savedArticle.getBody()).isEqualTo(article.getBody());
    }
    @Test
    @DisplayName("유저가 작성한 게시글 조회가 잘 되는지 확인")
    void 게시글_유저별조회_성공_테스트() {

        Member member = Member.builder().id(USER_ID).email("chs98412@naver,com").nickname("hyuksoon").thumbnail("img").build();
        memberRepository.save(member);
        Article article1 = new Article(1l, member, "제목1", "내용1",0, LocalDate.now());
        Article article2 = new Article(2l, member, "제목2", "내용2",0, LocalDate.now());
        Article article3 = new Article(3l, member, "제목3", "내용3",0, LocalDate.now());
        articleRepository.save(article1);
        articleRepository.save(article2);
        articleRepository.save(article3);
        List<Article> articles = new ArrayList<>();
        articles.add(article1);
        articles.add(article2);
        articles.add(article3);

        List<Article> response = articleRepository.findAllByMember(member);

        for (int i = 0; i < response.size(); i++) {
            assertThat(response.get(i).getId()).isEqualTo(articles.get(i).getId());
            assertThat(response.get(i).getTitle()).isEqualTo(articles.get(i).getTitle());
            assertThat(response.get(i).getBody()).isEqualTo(articles.get(i).getBody());
        }
    }


    @Test
    @Transactional
    @DisplayName("zero오프셋페이징 조회가 잘 되는지 확인")
    void 게시글_zero오프셋페이징_성공_테스트() {

        Member member = Member.builder().email("chs98412@naver,com").nickname("hyuksoon").thumbnail("img").build();
        memberRepository.save(member);
        List<Article> articles = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Article article = new Article(Integer.toUnsignedLong(i), member, "제목" + Integer.toString(i), "내용" + Integer.toString(i), 0, LocalDate.now());
            articles.add(article);
            articleRepository.save(article);
        }

        List<Article> response = articleRepository.zeroOffsetPaging(2l,3);

        for (int i=3;i<response.size();i++) {
            assertThat(response.get(i).getId()).isEqualTo(articles.get(i).getId());
            assertThat(response.get(i).getTitle()).isEqualTo(articles.get(i).getTitle());
            assertThat(response.get(i).getBody()).isEqualTo(articles.get(i).getBody());
        }
    }


    @Test
    @DisplayName("N+1 문제 확인용 테스트")
    void N플러스1문제_테스트() {

        System.out.println("Read 시작 시점");
        List<Article> response = articleRepository.findAll();
        System.out.println("N+1 문제 시작 시점");
        for (Article article : response) {
            System.out.println(article.getTitle());
            Member getMember = article.getMember();
            System.out.println(getMember);
        }

    }
}