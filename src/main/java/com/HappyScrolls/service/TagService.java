package com.HappyScrolls.service;

import com.HappyScrolls.dto.CartDTO;
import com.HappyScrolls.dto.TagDTO;
import com.HappyScrolls.entity.Article;
import com.HappyScrolls.entity.ArticleTag;
import com.HappyScrolls.entity.Tag;
import com.HappyScrolls.repository.ArticleTagRepository;
import com.HappyScrolls.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private ArticleTagRepository articleTagRepository;


    public void tagCreate(Article article, List<TagDTO.Request> tags) {
        for (TagDTO.Request dto : tags) {
            Tag tag;
            Optional<Tag> opTag = tagRepository.findByBody(dto.getBody());
            if (opTag.isPresent()) {
                tag = opTag.get();
            } else {
                tag = Tag.builder().body(dto.getBody()).build();
                tagRepository.save(tag);
            }
            ArticleTag articleTag = ArticleTag.builder().article(article).tag(tag).build();
            articleTagRepository.save(articleTag);
        }
    }

    public List<TagDTO.Response> tagsRetrieve(Article article) {
        List<ArticleTag> articleTags = articleTagRepository.findByArticle(article);


        return articleTags.stream()
                .map(articleTag -> TagDTO.Response.builder()
                        .id(articleTag.getTag().getId())
                        .body(articleTag.getTag().getBody())
                        .build())
                .collect(Collectors.toList());
    }

    public Tag tagFind(String tag) {
        return tagRepository.findByBody(tag).orElseThrow(()-> new NoSuchElementException(String.format("tag[%s] 태그를 찾을 수 없습니다", tag)));

    }

    public List<ArticleTag> articlrTagRetrieveByTag(Tag findTag) {
        return articleTagRepository.findAllByTag(findTag);
    }

    public List<ArticleTag> articlrTagRetrieveByTagList(List<Tag> tags) {
        return articleTagRepository.findAllByTagIn(tags);
    }
}
