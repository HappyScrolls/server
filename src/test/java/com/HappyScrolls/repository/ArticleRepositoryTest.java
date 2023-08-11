package com.HappyScrolls.repository;

import com.HappyScrolls.dto.ArticleDTO;
import com.HappyScrolls.entity.Article;
import com.HappyScrolls.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@DataJpaTest
public class ArticleRepositoryTest {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private MemberRepository memberRepository;
    private static final Long USER_ID = 1L;


    @Test
    @DisplayName("게시글 DB에 저장이 잘 되는지 확인")
    void 게시글_저장_성공_테스트() {

       Member member = Member.builder().id(USER_ID).email("chs98412@naver,com").nickname("hyuksoon").thumbnail("img").build();
        memberRepository.save(member);
        ArticleDTO.Request request = ArticleDTO.Request.builder().title("제목").body("내용").build();

        Article article = request.toEntity();
        article.setId(1l);
        article.setMember(member);

        Article savedArticle = articleRepository.save(article);
        assertThat(savedArticle).isEqualTo(article);
    }
    @Test
    @DisplayName("유저가 작성한 게시글 조회가 잘 되는지 확인")
    void 게시글_유저별조회_성공_테스트() {

        Member member = Member.builder().id(USER_ID).email("chs98412@naver,com").nickname("hyuksoon").thumbnail("img").build();
        memberRepository.save(member);
        Article article1 = new Article(1l, member, "제목1", "내용1");
        Article article2 = new Article(2l, member, "제목2", "내용2");
        Article article3 = new Article(3l, member, "제목3", "내용3");
        articleRepository.save(article1);
        articleRepository.save(article2);
        articleRepository.save(article3);
        List<Article> articles = new ArrayList<>();
        articles.add(article1);
        articles.add(article2);
        articles.add(article3);

        System.out.println("Read 시작 시점");
        List<Article> response = articleRepository.findAllByMember(member);

        assertThat(response).isEqualTo(articles);
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