package com.HappyScrolls.domain.comment.controller;

import com.HappyScrolls.domain.comment.dto.CommentDTO;
import com.HappyScrolls.domain.member.entity.Member;
import com.HappyScrolls.domain.comment.service.CommentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<Long> createParentComment(@AuthenticationPrincipal Member member, @RequestBody CommentDTO.ParentRequest request) {
        Long response = commentService.commentParentCreate(member, request);
        return  ResponseEntity.ok(response);
    }

    @ApiOperation(value = "대댓글 생성")
    @PostMapping("/child")
    public ResponseEntity<Long> createChildComment(@AuthenticationPrincipal Member member, @RequestBody CommentDTO.ChildRequest request) {
        Long response = commentService.commentChildCreate(member, request);
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "게시글 id로 댓글 조회")
    @GetMapping("")
    public ResponseEntity< List<CommentDTO.Response> > retrieveParentComment(@RequestParam Long id) {
        return ResponseEntity.ok(commentService.commentRetrieve(id));
    }



    @ApiOperation(value = "댓글 수정")
    @PutMapping("")
    public ResponseEntity<Long> editComment(@AuthenticationPrincipal Member member, @RequestBody CommentDTO.Edit request) {
        return ResponseEntity.ok(commentService.commentEdit(member, request));
    }

    @ApiOperation(value = "댓글 삭제")
    @DeleteMapping("")
    public ResponseEntity<Integer> deleteComment(@AuthenticationPrincipal Member member, @RequestParam Long id) {
        return ResponseEntity.ok(commentService.commentDelete(member, id));
    }



}
