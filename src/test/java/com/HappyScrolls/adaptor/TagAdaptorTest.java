package com.HappyScrolls.adaptor;

import com.HappyScrolls.domain.tag.adaptor.TagAdaptor;
import com.HappyScrolls.domain.tag.entity.ArticleTag;
import com.HappyScrolls.domain.tag.entity.Tag;
import com.HappyScrolls.domain.tag.repository.ArticleTagRepository;
import com.HappyScrolls.domain.tag.repository.TagRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TagAdaptorTest {

    @InjectMocks
    private TagAdaptor tagAdaptor;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private ArticleTagRepository articleTagRepository;

    @Test
    @DisplayName("태그 생성 테스트")
    public void 태그_생성_테스트() {
        Tag mockTag = Tag.builder().id(1l).build();

        when(tagRepository.save(any(Tag.class))).thenReturn(mockTag);

        Long resultId = tagAdaptor.tagCreate(new Tag());
        assertEquals(1L, resultId);
    }

    @Test
    @DisplayName("태그 개수 조회 테스트")
    public void 태그_개수_조회_테스트() {
        when(tagRepository.countByBody(any(String.class))).thenReturn(1L);

        Long resultCount = tagAdaptor.count("example");
        assertEquals(1L, resultCount);
    }

    @Test
    @DisplayName("태그 검색 테스트")
    public void 태그_검색_테스트() {
        Tag mockTag = Tag.builder().body("test").build();

        when(tagRepository.findByBody(any(String.class))).thenReturn(Optional.of(mockTag));

        Tag resultTag = tagAdaptor.tagFind("test");
        assertEquals("test", resultTag.getBody());
    }

    @Test
    @DisplayName("태그 검색 실패 테스트")
    public void 태그_검색_실패_테스트() {
        when(tagRepository.findByBody(any(String.class))).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> tagAdaptor.tagFind("test"));
    }

    @Test
    @DisplayName("태그 리스트 검색 테스트")
    public void 태그_리스트_검색_테스트() {
        Tag tag1 = Tag.builder().body("test1").build();

        Tag tag2 = Tag.builder().body("test2").build();

        List<String> tagNames = List.of("test1", "test2");
        when(tagRepository.findByBodyIn(any(List.class))).thenReturn(List.of(tag1, tag2));

        List<Tag> resultTags = tagAdaptor.tagsFind(tagNames);
        assertEquals(2, resultTags.size());
        assertEquals(resultTags, List.of(tag1, tag2));
    }

    @Test
    @DisplayName("articletag 목록 검색 테스트")
    public void articleTag_목록_검색_테스트() {
        Tag tag= Tag.builder().body("test1").build();

        ArticleTag articleTag1 = new ArticleTag();
        ArticleTag articleTag2 = new ArticleTag();

        when(articleTagRepository.findAllByTag(any(Tag.class))).thenReturn(List.of(articleTag1, articleTag2));

        List<ArticleTag> resultArticleTags = tagAdaptor.articlrTagRetrieveByTag(tag);
        assertEquals(2, resultArticleTags.size());
        assertEquals(resultArticleTags,List.of(articleTag1, articleTag2));
    }
}
