package com.HappyScrolls.service;

import com.HappyScrolls.dto.ProductDTO;
import com.HappyScrolls.dto.TagDTO;
import com.HappyScrolls.entity.*;
import com.HappyScrolls.repository.ArticleTagRepository;
import com.HappyScrolls.repository.ProductRepository;
import com.HappyScrolls.repository.TagRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {

    @Mock
    private TagRepository tagRepository;
    @Mock
    private ArticleTagRepository articleTagRepository;
    @InjectMocks
    private TagService tagService;

    //수정 필요
    @Test
    @DisplayName("태그 생성 기능이 제대로 동작하는지 확인")
    void 태그_생성_성공_테스트() {

        Article article = Article.builder().id(1l).title("제목1").body("내용1").viewCount(0).createDate(LocalDate.now()).sticker(Sticker.NEWHIT).build();

        List<TagDTO.Request> request = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            TagDTO.Request dto = TagDTO.Request.builder().body("tag" + Integer.toString(i)).build();
            request.add(dto);
        }

        Tag tag = Tag.builder().body(request.get(0).getBody()).build();
        when(tagRepository.findByBody(any())).thenReturn(Optional.empty());
        when(tagRepository.save(any())).thenReturn(tag);
        when(articleTagRepository.save(any())).thenReturn(any());

        tagService.tagCreate(article, request);
//????
        //verify(productRepository).save(product);
//        assertThat(response.getName()).isEqualTo(product.getName());
//        assertThat(response.getDescription()).isEqualTo(product.getDescription());
//        assertThat(response.getPrice()).isEqualTo(product.getPrice());

    }


    //수정 필요
    @Test
    @DisplayName("태그 조회 기능이 제대로 동작하는지 확인")
    void 게시글에_달린_태그_조회_성공_테스트() {
        Article article = Article.builder().id(1l).title("제목1").body("내용1").viewCount(0).createDate(LocalDate.now()).sticker(Sticker.NEWHIT).build();
        Tag tag1 = new Tag(1l, "tag1");
        Tag tag2 = new Tag(1l, "tag2");
        ArticleTag articleTag1 = new ArticleTag(1l, article, tag1);
        ArticleTag articleTag2 = new ArticleTag(1l, article, tag2);

        List<ArticleTag> articleTags = new ArrayList<>();
        articleTags.add(articleTag1);
        articleTags.add(articleTag2);
        when(articleTagRepository.findByArticle(any())).thenReturn(articleTags);
        List<TagDTO.Response> response = tagService.tagsRetrieve(article);

        assertThat(response.get(0).getBody()).isEqualTo(tag1.getBody());
        assertThat(response.get(1).getBody()).isEqualTo(tag2.getBody());


    }
    @Test
    @DisplayName("태그  조회 기능이 제대로 동작하는지 확인")
    void 태그_조회_성공_테스트() {

        String tagname = "tag1";
        Tag tag= new Tag(1l, tagname);
        when(tagRepository.findByBody(any())).thenReturn(Optional.of(tag));


        Tag response = tagService.tagFind(tagname);

        verify(tagRepository).findByBody(tagname);
        assertThat(response).isEqualTo(tag);

    }

    @Test
    @DisplayName("태그  조회  기능이 조회를 할 수 없을 때 예외처리를 하는지 확인")
    void 태그_단건조회_예외_테스트() {
        String tagname = "tag1";
        when(tagRepository.findByBody(any())).thenReturn(Optional.empty());


        assertThrows(NoSuchElementException.class, () -> tagService.tagFind(tagname));


        verify(tagRepository).findByBody(tagname);
    }
    @Test
    @DisplayName("태그  조회 기능이 제대로 동작하는지 확인")
    void 태그기준으로_조회_성공_테스트() {

        Article article1 = Article.builder().id(1l).title("제목1").body("내용1").viewCount(0).createDate(LocalDate.now()).sticker(Sticker.NEWHIT).build();
        Article article2 = Article.builder().id(2l).title("제목1").body("내용1").viewCount(0).createDate(LocalDate.now()).sticker(Sticker.NEWHIT).build();

        Tag tag1 = new Tag(1l, "tag1");
        ArticleTag articleTag1 = new ArticleTag(1l, article1, tag1);
        ArticleTag articleTag2 = new ArticleTag(1l, article1, tag1);

        List<ArticleTag> articleTags = new ArrayList<>();
        articleTags.add(articleTag1);
        articleTags.add(articleTag2);

        when(articleTagRepository.findAllByTag(any())).thenReturn(articleTags);
        List<ArticleTag> response = tagService.articlrTagRetrieveByTag(tag1);

        assertThat(response.get(0).getTag().getBody()).isEqualTo(tag1.getBody());
        assertThat(response.get(0).getArticle().getId()).isEqualTo(article1.getId());

        assertThat(response.get(1).getTag().getBody()).isEqualTo(tag1.getBody());
        assertThat(response.get(1).getArticle().getId()).isEqualTo(article1.getId());



    }

}