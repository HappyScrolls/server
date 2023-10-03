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

    @GetMapping("/test")
    public ResponseEntity testAPI() {
        return ResponseEntity.ok(null);
    }


    @ApiOperation(value = "모든 게시글 페이징 조회")
    @GetMapping("/paging")
    public ResponseEntity<List<ArticleDTO.ListResponse>> retrieveAllArticlePage(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        List<Article> response = articleService.articleRetrievePaging(pageRequest);

        return  ResponseEntity.ok(toResponseDtoList(response));
    }


    @ApiOperation(value = "모든 게시글 페이징 조회 제로 오프셋")
    @GetMapping("/zeropaging")
    public ResponseEntity<List<ArticleDTO.ListResponse>> retrieveAllArticlePagewithZeroOffset(@RequestParam Long lastindex, @RequestParam Integer limit) {
        List<Article> response = articleService.articleRetrievePagingWithZeroOffset(lastindex,limit);

        return ResponseEntity.ok(toResponseDtoList(response));
    }

    @ApiOperation(value = "게시글 id로 단건 조회")
    @GetMapping("")
    public ResponseEntity<ArticleDTO.DetailResponse> retrieveArticle(@AuthenticationPrincipal Member member, @RequestParam Long id) {

        System.out.println("!!!!!!!!!@@@@"+member);
        Article response = articleService.articleRetrieve(id);
        viewCountService.viewCountIncrease(id);
        return ResponseEntity.ok(toResponseDto(response));
    }

    @ApiOperation(value = "태그별 모든 게시글 조회")
    @GetMapping("/tag")
    public ResponseEntity<List<ArticleDTO.ListResponse>> retrieveAllArticleByTag(@RequestParam String tag) {
        List<Article> response = articleService.articleRetrieveByTag(tag);
        return  ResponseEntity.ok(toResponseDtoList(response));
    }

    @ApiOperation(value = "태그별 모든 게시글 페이징 조회")
    @GetMapping("/tagpaging")
    public ResponseEntity<List<ArticleDTO.ListResponse>> retrieveAllArticleByTagPaging(@RequestParam Long lastindex,@RequestParam String tag) {
        List<Article> response = articleService.articleRetrieveByTagPaging(lastindex,tag);
        return  ResponseEntity.ok(toResponseDtoList(response));
    }

    @ApiOperation(value = "태그별 모든 게시글 페이징 조회2")
    @GetMapping("/tagpaging2")
    public ResponseEntity<List<ArticleDTO.ListResponse>> retrieveAllArticleByTagPaging2(@RequestParam Long lastindex,@RequestParam String tag) {
        List<Article> response = articleService.articleRetrieveByTagPaging2(lastindex,tag);
        return  ResponseEntity.ok(toResponseDtoList(response));
    }

    @ApiOperation(value = "다중 태그별 모든 게시글 조회")
    @GetMapping("/taglist")
    public ResponseEntity retrieveAllArticleByTagList(@RequestBody TagDTO.ListRequest request) {
        List<Article> response = articleService.articleRetrieveByTagList(request);
        return new ResponseEntity(toResponseDtoList(response), HttpStatus.ACCEPTED);
    }
    @ApiOperation(value = "특정 유저가 작성한 모든 게시글 조회")
    @GetMapping("/user")
    public ResponseEntity<List<ArticleDTO.ListResponse>> retrieveUserArticle(@RequestParam String email) {
        List<Article> response = articleService.userArticleRetrieve(email);
        return  ResponseEntity.ok(toResponseDtoList(response));
    }

    @ApiOperation(value = "게시글 작성")
    @PostMapping("")
    public ResponseEntity<Long> createArticle(@AuthenticationPrincipal Member member, @RequestBody ArticleDTO.Request request) {
        System.out.println("!!!!"+member+member.getEmail());
        Long response = articleService.articleCreate(member,request);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "게시글 수정")
    @PutMapping("")
    public ResponseEntity<Long> editArticle(@AuthenticationPrincipal Member member, @RequestBody ArticleDTO.Edit request) {
        Long response = articleService.articleEdit(member,request);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "게시글 삭제")
    @DeleteMapping("")
    public ResponseEntity<Integer> deleteArticle(@AuthenticationPrincipal Member member,@RequestParam Long id) {
        articleService.articleDelete(member,id);
        return  ResponseEntity.ok(1); //삭제한 로우 수를 반환
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
