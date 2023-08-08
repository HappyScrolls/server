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
        List<ArticleDTO.Response> response = articleService.articleRetrieveAll();
        return new ResponseEntity(response, HttpStatus.ACCEPTED);
    }

    @GetMapping("")
    public ResponseEntity retrieveArticle(@RequestParam Long id) {
        ArticleDTO.Response response = articleService.articleRetrieve(id);
        return new ResponseEntity(response, HttpStatus.ACCEPTED);
    }

    @GetMapping("/user")
    public ResponseEntity retrieveUserArticle(@RequestParam String email) {
        ArticleDTO.Response response = articleService.userArticleRetrieve(email);
        return new ResponseEntity(response, HttpStatus.ACCEPTED);
    }

    @PostMapping("")
    public ResponseEntity createArticle(@AuthenticationPrincipal Member member, @RequestBody ArticleDTO.Request request) {
        System.out.println("!!!! "+member);

        ArticleDTO.Response response = articleService.articleCreate(member,request);

        return new ResponseEntity(response, HttpStatus.CREATED);
    }

    @PutMapping("")
    public ResponseEntity editArticle(@AuthenticationPrincipal Member member, @RequestBody ArticleDTO.Edit request) {
        ArticleDTO.Response response = articleService.articleEdit(member,request);
        return new ResponseEntity(response, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("")
    public ResponseEntity deleteArticle(@AuthenticationPrincipal Member member,@RequestParam Long id) {
        articleService.articleDelete(member,id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
