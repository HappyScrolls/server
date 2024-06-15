package com.HappyScrolls.domain.tag.adaptor;

import com.HappyScrolls.config.Adaptor;
import com.HappyScrolls.domain.article.entity.Article;
import com.HappyScrolls.domain.tag.entity.ArticleTag;
import com.HappyScrolls.domain.tag.entity.Tag;
import com.HappyScrolls.domain.tag.repository.ArticleTagRepository;
import com.HappyScrolls.domain.tag.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.NoSuchElementException;

@Adaptor
public class TagAdaptor {

    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private ArticleTagRepository articleTagRepository;


    public Long tagCreate(Tag reqeust) {
        return tagRepository.save(reqeust).getId();
    }
    public Long articleTagCreate(ArticleTag reqeust) {
        return articleTagRepository.save(reqeust).getId();
    }

    public Long count(String body) {
        return tagRepository.countByBody(body);
    }

    public List<ArticleTag> tagsRetrieve(Article article) {
        return articleTagRepository.findByArticle(article);
    }

    public Tag tagFind(String tag) {
        return tagRepository.findByBody(tag).orElseThrow(()-> new NoSuchElementException(String.format("tag[%s] 태그를 찾을 수 없습니다", tag)));
    }
    public List<Tag> tagsFind(List<String> tags) {
        return tagRepository.findByBodyIn(tags);
    }

    public List<ArticleTag> articlrTagRetrieveByTag(Tag findTag) {
        return articleTagRepository.findAllByTag(findTag);
    }

    public List<ArticleTag> articlrTagRetrieveByTagList(List<Tag> tags) {
        return articleTagRepository.findAllByTagIn(tags);
    }


    public List<ArticleTag> articlrTagRetrieveByTagPaging(Long lastindex, Tag tag) {
        return articleTagRepository.findByTagPaging(lastindex,tag);
    }
}
