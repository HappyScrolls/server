package com.HappyScrolls.service;

import com.HappyScrolls.adaptor.ArticleAdaptor;
import com.HappyScrolls.adaptor.TagAdaptor;
import com.HappyScrolls.dto.ArticleDTO;
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
    private TagAdaptor tagAdaptor;
    @Autowired
    private ArticleAdaptor articleAdaptor;

    //옮기기
    public List<ArticleDTO.ListResponse> articleRetrieveByTag(String tag) {
        Tag findTag = tagAdaptor.tagFind(tag);
        List<ArticleTag> articleTags = tagAdaptor.articlrTagRetrieveByTag(findTag);

        return ArticleDTO.ListResponse.toResponseDtoList(articleTags.stream()
                .map(articleTag -> articleTag.getArticle())
                .collect(Collectors.toList()));
    }


    //이동해야 하는 코드
    public List<ArticleDTO.ListResponse> articleRetrieveByTagPaging( Long lastindex, String tag) {

        Tag findTag = tagAdaptor.tagFind(tag);
        List<ArticleTag> articleTags = tagAdaptor.articlrTagRetrieveByTagPaging(lastindex,findTag);

        return ArticleDTO.ListResponse.toResponseDtoList(articleTags.stream()
                .map(articleTag -> articleTag.getArticle())
                .collect(Collectors.toList()));
    }

    //이동해야 하는 코드
    public List<ArticleDTO.ListResponse> articleRetrieveByTagPaging2(Long lastindex, String tag) {

        Tag findTag = tagAdaptor.tagFind(tag);
        return ArticleDTO.ListResponse.toResponseDtoList( articleAdaptor.articleRetrieveByTagPaging2(lastindex,tag));

    }

    public void tagCreate(Article article, List<TagDTO.Request> tags) {
        for (TagDTO.Request dto : tags) {
            Tag tag;
            if (tagAdaptor.count(dto.getBody()).equals(1l)) {
                tag = tagAdaptor.tagFind(dto.getBody());
            }
            else {
                tag = Tag.builder().body(dto.getBody()).build();
                tagAdaptor.tagCreate(tag);
            }

            ArticleTag articleTag = ArticleTag.builder().article(article).tag(tag).build();
            tagAdaptor.articleTagCreate(articleTag);
        }
    }
    public List<TagDTO.Response> tagsRetrieve(Article article) {
        List<ArticleTag> articleTags = tagAdaptor.tagsRetrieve(article);

        return articleTags.stream()
                .map(articleTag -> TagDTO.Response.builder()
                        .id(articleTag.getTag().getId())
                        .body(articleTag.getTag().getBody())
                        .build())
                .collect(Collectors.toList());
    }

}
