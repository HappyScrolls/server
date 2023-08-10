package com.HappyScrolls.repository;

import com.HappyScrolls.dto.ArticleDTO;
import com.HappyScrolls.entity.Article;
import com.HappyScrolls.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ArticleRepositoryTest {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private MemberRepository memberRepository;
    private static final Long USER_ID = 1L;
    @Test
    @DisplayName("게시글이 DB에 저장이 잘 되는지 확인")
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


}