package com.HappyScrolls.controller;


import com.HappyScrolls.dto.ArticleDTO;
import com.HappyScrolls.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("")
    public ResponseEntity createArticle(@RequestBody ArticleDTO.Request request) {

        ArticleDTO.Response response = articleService.articleCreate(request);

        return new ResponseEntity(response, HttpStatus.CREATED);
    }

    @PutMapping("")
    public ResponseEntity editArticle(@RequestBody ArticleDTO.edit request) {
        ArticleDTO.Response response = articleService.articleEdit(request);
        return new ResponseEntity(response, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("")
    public ResponseEntity editArticle(@RequestParam Long id) {
        articleService.articleDelete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
