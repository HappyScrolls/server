package com.HappyScrolls.controller;

import com.HappyScrolls.dto.CommentDTO;
import com.HappyScrolls.entity.Member;
import com.HappyScrolls.service.ArticleService;
import com.HappyScrolls.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("comment")
public class CommentController {


    @Autowired
    private CommentService commentService;

    @PostMapping("")
    public ResponseEntity createComment(@AuthenticationPrincipal Member member, @RequestBody CommentDTO.Request request) {
        CommentDTO.Response response = commentService.commentCreate(member, request);
        return new ResponseEntity(response, HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity retrieveComment(@RequestParam Long id) {
        List<CommentDTO.Response> response = commentService.commentRetrieve(id);

        return new ResponseEntity(response, HttpStatus.ACCEPTED);
    }


}
