package com.HappyScrolls.controller;


import com.HappyScrolls.dto.ArticleDTO;
import com.HappyScrolls.dto.TagDTO;
import com.HappyScrolls.entity.Member;
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
    public ResponseEntity<List<ArticleDTO.ListResponse>> retrieveAllArticlePage(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        List<ArticleDTO.ListResponse> response = articleService.articleRetrievePaging(pageRequest);

        return new ResponseEntity(response, HttpStatus.ACCEPTED);
    }

    @ApiOperation(value = "모든 게시글 페이징 조회 제로 오프셋")
    @GetMapping("/zeropaging")
    public ResponseEntity<List<ArticleDTO.ListResponse>> retrieveAllArticlePagewithZeroOffset(@RequestParam Long lastindex, @RequestParam Integer limit) {
        List<ArticleDTO.ListResponse> response = articleService.articleRetrievePagingWithZeroOffset(lastindex,limit);

        return new ResponseEntity(response, HttpStatus.ACCEPTED);
    }

    @ApiOperation(value = "모든 게시글 커버링 인덱스")
    @GetMapping("/coveringaging")
    public ResponseEntity<List<ArticleDTO.ListResponse>> retrieveAllArticlePagewithCoveringIndex(@RequestParam Integer page, @RequestParam Integer limit) {
        List<ArticleDTO.ListResponse> response = articleService.articleRetrievePagingWithCoveringIndex(page,limit);

        return new ResponseEntity(response, HttpStatus.ACCEPTED);
    }

    @ApiOperation(value = "게시글 id로 단건 조회")
    @GetMapping("")
    public ResponseEntity retrieveArticle(@RequestParam Long id) {
        ArticleDTO.DetailResponse detailResponse = articleService.articleRetrieve(id);
        viewCountService.viewCountIncrease(id);
        return new ResponseEntity(detailResponse, HttpStatus.ACCEPTED);
    }

    @ApiOperation(value = "태그별 모든 게시글 조회")
    @GetMapping("/tag")
    public ResponseEntity retrieveAllArticleByTag(@RequestParam String tag) {
        List<ArticleDTO.ListResponse> detailResponse = articleService.articleRetrieveByTag(tag);
        return new ResponseEntity(detailResponse, HttpStatus.ACCEPTED);
    }

    @ApiOperation(value = "다중 태그별 모든 게시글 조회")
    @GetMapping("/taglist")
    public ResponseEntity retrieveAllArticleByTagList(@RequestBody TagDTO.ListRequest request) {
        List<ArticleDTO.ListResponse> response = articleService.articleRetrieveByTagList(request);
        return new ResponseEntity(response, HttpStatus.ACCEPTED);
    }
    @ApiOperation(value = "특정 유저가 작성한 모든 게시글 조회")
    @GetMapping("/user")
    public ResponseEntity retrieveUserArticle(@RequestParam String email) {
        List<ArticleDTO.DetailResponse> detailResponse = articleService.userArticleRetrieve(email);
        return new ResponseEntity(detailResponse, HttpStatus.ACCEPTED);
    }

    @ApiOperation(value = "게시글 작성")
    @PostMapping("")
    public ResponseEntity createArticle(@AuthenticationPrincipal Member member, @RequestBody ArticleDTO.Request request) {

        ArticleDTO.DetailResponse detailResponse = articleService.articleCreate(member,request);

        return new ResponseEntity(detailResponse, HttpStatus.CREATED);
    }

    @ApiOperation(value = "게시글 수정")
    @PutMapping("")
    public ResponseEntity editArticle(@AuthenticationPrincipal Member member, @RequestBody ArticleDTO.Edit request) {
        ArticleDTO.DetailResponse detailResponse = articleService.articleEdit(member,request);
        return new ResponseEntity(detailResponse, HttpStatus.ACCEPTED);
    }

    @ApiOperation(value = "게시글 삭제")
    @DeleteMapping("")
    public ResponseEntity deleteArticle(@AuthenticationPrincipal Member member,@RequestParam Long id) {
        articleService.articleDelete(member,id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
