package com.HappyScrolls.controller;

import com.HappyScrolls.dto.CommentDTO;
import com.HappyScrolls.entity.Member;
import com.HappyScrolls.service.CommentService;
import io.swagger.annotations.ApiOperation;
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


    @ApiOperation(value = "댓글 생성")
    @PostMapping("")
    public ResponseEntity createParentComment(@AuthenticationPrincipal Member member, @RequestBody CommentDTO.ParentRequest request) {
        CommentDTO.ParentResponse response = commentService.commentParentCreate(member, request);
        return new ResponseEntity(response, HttpStatus.CREATED);
    }

    @ApiOperation(value = "대댓글 생성")
    @PostMapping("/child")
    public ResponseEntity<CommentDTO.ChildResponse> createChildComment(@AuthenticationPrincipal Member member, @RequestBody CommentDTO.ChildRequest request) {
        CommentDTO.ChildResponse response = commentService.commentChildCreate(member, request);
        return new ResponseEntity<CommentDTO.ChildResponse>(response, HttpStatus.CREATED);
    }

    @ApiOperation(value = "게시글 id로 댓글 조회")
    @GetMapping("")
    public ResponseEntity<CommentDTO.Response> retrieveParentComment(@RequestParam Long id) {
        List<CommentDTO.Response> response = commentService.commentRetrieve(id);
        return new ResponseEntity(response, HttpStatus.ACCEPTED);
    }



    @ApiOperation(value = "댓글 수정")
    @PutMapping("")
    public ResponseEntity editComment(@AuthenticationPrincipal Member member, @RequestBody CommentDTO.Edit request) {
        CommentDTO.ParentResponse response = commentService.commentEdit(member, request);

        return new ResponseEntity(response, HttpStatus.CREATED);
    }

    @ApiOperation(value = "댓글 수정")
    @DeleteMapping("")
    public ResponseEntity deleteComment(@AuthenticationPrincipal Member member, @RequestParam Long id) {
        commentService.commentDelete(member, id);
        return new ResponseEntity( HttpStatus.ACCEPTED);
    }
}
