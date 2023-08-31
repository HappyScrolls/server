package com.HappyScrolls.controller;


import com.HappyScrolls.dto.ArticleDTO;
import com.HappyScrolls.dto.ProductDTO;
import com.HappyScrolls.dto.TagDTO;
import com.HappyScrolls.entity.Article;
import com.HappyScrolls.entity.Member;
import com.HappyScrolls.entity.Product;
import com.HappyScrolls.entity.ViewCount;
import com.HappyScrolls.service.ArticleService;
import com.HappyScrolls.service.ViewCountService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ViewCountService viewCountService;

//    @GetMapping("/all")
//    public ResponseEntity retrieveAllArticle() {
//        List<ArticleDTO.ListResponse> response = articleService.articleRetrieveAll();
//        return new ResponseEntity(response, HttpStatus.ACCEPTED);
//    }

    @ApiOperation(value = "모든 게시글 페이징 조회")
    @GetMapping("/paging")
    public ResponseEntity retrieveAllArticlePage(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        List<Article> response = articleService.articleRetrievePaging(pageRequest);

        return new ResponseEntity(toResponseDtoList(response), HttpStatus.ACCEPTED);
    }


    @ApiOperation(value = "모든 게시글 페이징 조회 제로 오프셋")
    @GetMapping("/zeropaging")
    public ResponseEntity retrieveAllArticlePagewithZeroOffset(@RequestParam Long lastindex, @RequestParam Integer limit) {
        List<Article> response = articleService.articleRetrievePagingWithZeroOffset(lastindex,limit);

        return new ResponseEntity(toResponseDtoList(response), HttpStatus.ACCEPTED);
    }

    @ApiOperation(value = "모든 게시글 커버링 인덱스")
    @GetMapping("/coveringaging")
    public ResponseEntity retrieveAllArticlePagewithCoveringIndex(@RequestParam Integer page, @RequestParam Integer limit) {
        List<Article> response = articleService.articleRetrievePagingWithCoveringIndex(page,limit);


        return new ResponseEntity(toResponseDtoList(response), HttpStatus.ACCEPTED);

    }

    @ApiOperation(value = "게시글 id로 단건 조회")
    @GetMapping("")
    public ResponseEntity retrieveArticle(@RequestParam Long id) {
        Article response = articleService.articleRetrieve(id);
        viewCountService.viewCountIncrease(id);
        return new ResponseEntity(toResponseDto(response), HttpStatus.ACCEPTED);
    }

    @ApiOperation(value = "태그별 모든 게시글 조회")
    @GetMapping("/tag")
    public ResponseEntity retrieveAllArticleByTag(@RequestParam String tag) {
        List<Article> response = articleService.articleRetrieveByTag(tag);
        return new ResponseEntity(toResponseDtoList(response), HttpStatus.ACCEPTED);
    }

    @ApiOperation(value = "다중 태그별 모든 게시글 조회")
    @GetMapping("/taglist")
    public ResponseEntity retrieveAllArticleByTagList(@RequestBody TagDTO.ListRequest request) {
        List<Article> response = articleService.articleRetrieveByTagList(request);
        return new ResponseEntity(toResponseDtoList(response), HttpStatus.ACCEPTED);
    }
    @ApiOperation(value = "특정 유저가 작성한 모든 게시글 조회")
    @GetMapping("/user")
    public ResponseEntity retrieveUserArticle(@RequestParam String email) {
        List<Article> response = articleService.userArticleRetrieve(email);
        return new ResponseEntity(toResponseDtoList(response), HttpStatus.ACCEPTED);
    }

    @ApiOperation(value = "게시글 작성")
    @PostMapping("")
    public ResponseEntity createArticle(@AuthenticationPrincipal Member member, @RequestBody ArticleDTO.Request request) {
        Article response = articleService.articleCreate(member,request);
        return new ResponseEntity(toResponseDto(response), HttpStatus.CREATED);
    }

    @ApiOperation(value = "게시글 수정")
    @PutMapping("")
    public ResponseEntity editArticle(@AuthenticationPrincipal Member member, @RequestBody ArticleDTO.Edit request) {
        Article response = articleService.articleEdit(member,request);
        return new ResponseEntity(toResponseDto(response), HttpStatus.ACCEPTED);
    }

    @ApiOperation(value = "게시글 삭제")
    @DeleteMapping("")
    public ResponseEntity deleteArticle(@AuthenticationPrincipal Member member,@RequestParam Long id) {
        articleService.articleDelete(member,id);
        return new ResponseEntity(HttpStatus.OK);
    }

    public static ArticleDTO. DetailResponse toResponseDto(Article article) {
        return ArticleDTO.DetailResponse.builder()
                .id(article.getId())
                .title(article.getTitle())
                .body(article.getBody())
                .build();
    }
    public static List<ArticleDTO.ListResponse> toResponseDtoList(List<Article> articles) {
        return articles.stream()
                .map(article -> ArticleDTO.ListResponse.builder()
                        .id(article.getId())
                        .title(article.getTitle())
                        .member(article.getMember().getNickname())
                        .build())
                .collect(Collectors.toList());
    }
}
