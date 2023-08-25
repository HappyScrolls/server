package com.HappyScrolls.controller;


import com.HappyScrolls.dto.ArticleDTO;
import com.HappyScrolls.entity.Member;
import com.HappyScrolls.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/all")
    public ResponseEntity retrieveAllArticle() {
        List<ArticleDTO.DetailResponse> detailResponse = articleService.articleRetrieveAll();
        return new ResponseEntity(detailResponse, HttpStatus.ACCEPTED);
    }

    @GetMapping("")
    public ResponseEntity retrieveArticle(@RequestParam Long id) {
        ArticleDTO.DetailResponse detailResponse = articleService.articleRetrieve(id);
        return new ResponseEntity(detailResponse, HttpStatus.ACCEPTED);
    }

    @GetMapping("/tag")
    public ResponseEntity retrieveAllArticleByTag(@RequestParam String tag) {
        List<ArticleDTO.ListResponse> detailResponse = articleService.articleRetrieveByTag(tag);
        return new ResponseEntity(detailResponse, HttpStatus.ACCEPTED);
    }

    @GetMapping("/user")
    public ResponseEntity retrieveUserArticle(@RequestParam String email) {
        List<ArticleDTO.DetailResponse> detailResponse = articleService.userArticleRetrieve(email);
        return new ResponseEntity(detailResponse, HttpStatus.ACCEPTED);
    }

    @PostMapping("")
    public ResponseEntity createArticle(@AuthenticationPrincipal Member member, @RequestBody ArticleDTO.Request request) {

        ArticleDTO.DetailResponse detailResponse = articleService.articleCreate(member,request);

        return new ResponseEntity(detailResponse, HttpStatus.CREATED);
    }

    @PutMapping("")
    public ResponseEntity editArticle(@AuthenticationPrincipal Member member, @RequestBody ArticleDTO.Edit request) {
        ArticleDTO.DetailResponse detailResponse = articleService.articleEdit(member,request);
        return new ResponseEntity(detailResponse, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("")
    public ResponseEntity deleteArticle(@AuthenticationPrincipal Member member,@RequestParam Long id) {
        articleService.articleDelete(member,id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
