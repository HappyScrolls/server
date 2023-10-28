package com.HappyScrolls.domain.article.controller;

import com.HappyScrolls.aop.ExeTimer;
import com.HappyScrolls.config.elastic.ArticleDoc;
import com.HappyScrolls.config.elastic.ArticleDocRepository;
import com.HappyScrolls.domain.article.dto.ArticleDTO;
import com.HappyScrolls.domain.article.service.ArticleService;
import com.HappyScrolls.domain.article.service.ViewCountService;
import com.HappyScrolls.domain.member.entity.Member;
import com.HappyScrolls.domain.tag.dto.TagDTO;
import io.swagger.annotations.ApiOperation;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("elk")
public class ArticleDocController {

    @Autowired
    private ArticleDocRepository articleDocRepository;
    @Autowired
    private ArticleService articleService;

    @GetMapping("/elk")
    public List<ArticleDoc> get() {
        return articleDocRepository.findAllByTitle("제목20");
    }

    @Autowired
    private  RestHighLevelClient client;

    private static final String INDEX = "articletest";

    //전체 쿼리 처리
    @GetMapping("/elk5")
    public ResponseEntity<List<ArticleDTO.ListResponse>> search22() throws IOException {

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder()
                .query(myQuery())
                .size(0);

        SearchRequest searchRequest = new SearchRequest(INDEX)
                .source(searchSourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        SearchHits hits = searchResponse.getHits();
        List<ArticleDoc> articles = new ArrayList<>();
        List<ArticleDTO.ListResponse> result = new ArrayList<>();
        for (SearchHit hit : hits) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();

            ArticleDTO.ListResponse response = ArticleDTO.ListResponse.builder()
                    .id((Long) sourceAsMap.get("id"))
                    .member((String) sourceAsMap.get("member"))
                    .title((String) sourceAsMap.get("title"))
                    .build();


            result.add(response);
        }
        return ResponseEntity.ok(result);
    }

    //query 부분
    private QueryBuilder myQuery(){
        return QueryBuilders.matchQuery("title","부하테스트");
    }


    @ExeTimer
    @GetMapping("/elk2")
    public ResponseEntity<List<ArticleDTO.ListResponse>> get2(@RequestParam String param) {
        return ResponseEntity.ok(ArticleDTO.ListResponse.toResponseDtoListFromArticleDocs(articleDocRepository.findAllByTitleContaining(param)));
    }
    @ExeTimer
    @GetMapping("/elk3")
    public ResponseEntity<List<ArticleDTO.ListResponse>> searchWithElasticSearch(@RequestParam String param,@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return ResponseEntity.ok(ArticleDTO.ListResponse.toResponseDtoListFromArticleDocs( articleDocRepository.findAllByTitleContaining(param,pageRequest).getContent()));
    }
    @ExeTimer
    @GetMapping("/elk4")
    public ResponseEntity get4(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);

        return ResponseEntity.ok(articleService.elkPage(pageRequest));
    }



    @Autowired
    private ViewCountService viewCountService;


    @GetMapping("/test")
    public ResponseEntity testAPI() {
        return ResponseEntity.ok(null);
    }

    @ExeTimer
    @ApiOperation(value = "모든 게시글 페이징 조회")
    @GetMapping("/paging")
    public ResponseEntity<List<ArticleDTO.ListResponse>> retrieveAllArticlePage(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);

        return  ResponseEntity.ok(articleService.articleRetrievePaging(pageRequest));
    }

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


    @ExeTimer
    @GetMapping("/search")
    public ResponseEntity<List<ArticleDTO.ListResponse>> search(@RequestParam Long lastindex, @RequestParam Integer limit,@RequestParam String param) {

        return ResponseEntity.ok( articleService.search(lastindex,limit,param));
    }

}
