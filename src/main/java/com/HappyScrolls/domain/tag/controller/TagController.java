package com.HappyScrolls.domain.tag.controller;
import com.HappyScrolls.domain.article.dto.ArticleDTO;
import com.HappyScrolls.domain.tag.service.TagService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("tag")
public class TagController {

    @Autowired
    private TagService tagService;

        @ApiOperation(value = "태그별 모든 게시글 조회")
    @GetMapping("/tag")
    public ResponseEntity<List<ArticleDTO.ListResponse>> retrieveAllArticleByTag(@RequestParam String tag) {
        return  ResponseEntity.ok( tagService.articleRetrieveByTag(tag));
    }

    @ApiOperation(value = "태그별 모든 게시글 페이징 조회")
    @GetMapping("/tagpaging")
    public ResponseEntity<List<ArticleDTO.ListResponse>> retrieveAllArticleByTagPaging(@RequestParam Long lastindex,@RequestParam String tag) {
        return  ResponseEntity.ok(tagService.articleRetrieveByTagPaging(lastindex,tag));
    }

    @ApiOperation(value = "태그별 모든 게시글 페이징 조회2")
    @GetMapping("/tagpaging2")
    public ResponseEntity<List<ArticleDTO.ListResponse>> retrieveAllArticleByTagPaging2(@RequestParam Long lastindex,@RequestParam String tag) {
        return  ResponseEntity.ok(tagService.articleRetrieveByTagPaging2(lastindex,tag));
    }
}
