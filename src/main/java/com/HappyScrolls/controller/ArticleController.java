package com.HappyScrolls.controller;


import com.HappyScrolls.adaptor.ArticleAdaptor;
import com.HappyScrolls.aop.ExeTimer;
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


//    @ApiOperation(value = "모든 게시글 페이징 조회")
//    @GetMapping("/paging")
//    public ResponseEntity<List<ArticleDTO.ListResponse>> retrieveAllArticlePage(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
//        PageRequest pageRequest = PageRequest.of(page, size);
//
//        return  ResponseEntity.ok(articleService.articleRetrievePaging(pageRequest));
//    }

    @ExeTimer
    @ApiOperation(value = "모든 게시글 페이징 조회 제로 오프셋")
    @GetMapping("/zeropaging")
    public ResponseEntity<List<ArticleDTO.ListResponse>> retrieveAllArticlePagewithZeroOffset(@RequestParam Long lastid, @RequestParam Integer limit) {
        return ResponseEntity.ok(articleService.retrieveAllPaging(lastid,limit));
    }

    @ApiOperation(value = "게시글 id로 단건 조회")
    @GetMapping("")
    public ResponseEntity<ArticleDTO.DetailResponse> retrieveArticle(@RequestParam Long id) {

        ArticleDTO.DetailResponse response = articleService.articleRetrieve(id) ;
        viewCountService.viewCountIncrease(id);
        return ResponseEntity.ok(response);
    }



    @ApiOperation(value = "다중 태그별 모든 게시글 조회")
    @GetMapping("/taglist")
    public ResponseEntity<List<ArticleDTO.ListResponse>> retrieveAllArticleByTagList(@RequestBody TagDTO.ListRequest request) {
        return ResponseEntity.ok(articleService.articleRetrieveByTagList(request));
    }
    @ApiOperation(value = "특정 유저가 작성한 모든 게시글 조회")
    @GetMapping("/user")
    public ResponseEntity<List<ArticleDTO.ListResponse>> retrieveUserArticle(@RequestParam String email) {
        return  ResponseEntity.ok(articleService.userArticleRetrieve(email));
    }

    @ApiOperation(value = "게시글 작성")
    @PostMapping("")
    public ResponseEntity<Long> createArticle(@AuthenticationPrincipal Member member, @RequestBody ArticleDTO.Request request) {
        return ResponseEntity.ok(articleService.articleCreate(member,request));
    }

    @ApiOperation(value = "게시글 수정")
    @PutMapping("")
    public ResponseEntity<Long> editArticle(@AuthenticationPrincipal Member member, @RequestBody ArticleDTO.Edit request) {
        return ResponseEntity.ok(articleService.articleEdit(member,request));
    }

    @ApiOperation(value = "게시글 삭제")
    @DeleteMapping("")
    public ResponseEntity<Integer> deleteArticle(@AuthenticationPrincipal Member member,@RequestParam Long id) {
        articleService.articleDelete(member,id);
        return  ResponseEntity.ok(1); //삭제한 로우 수를 반환
    }


    @ApiOperation(value = "모든 게시글 페이징 조회 제로 오프셋")
    @GetMapping("/search")
    public ResponseEntity<List<ArticleDTO.ListResponse>> search(@RequestParam Long lastindex, @RequestParam Integer limit,@RequestParam String param) {

        return ResponseEntity.ok(articleService.search(lastindex,limit,param));
    }

}
