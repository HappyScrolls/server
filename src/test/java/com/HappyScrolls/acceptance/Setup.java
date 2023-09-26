package com.HappyScrolls.acceptance;

import com.HappyScrolls.config.JwtTokenUtil;
import com.HappyScrolls.dto.ArticleDTO;
import com.HappyScrolls.entity.Article;
import com.HappyScrolls.entity.Member;
import com.HappyScrolls.repository.ArticleRepository;
import com.HappyScrolls.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Setup {
    @Autowired
    private ArticleRepository articleRepository;
    Member member;
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    public Long saveArticle() {
        ArticleDTO.Request request = ArticleDTO.Request.builder().title("제목").body("내용").tags(null).build();
        Article article = request.toEntity();
        articleRepository.save(article);

        member = Member.builder().nickname("chs").point(100).email("@naver.com").build();
        memberRepository.save(member);

        return article.getId();
    }

    public String tk() {
        return jwtTokenUtil.generateToken("aadw",member.getEmail(),member.getEmail(),"awd");
    }
}