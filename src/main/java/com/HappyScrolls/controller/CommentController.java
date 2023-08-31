package com.HappyScrolls.controller;

import com.HappyScrolls.dto.CommentDTO;
import com.HappyScrolls.dto.ProductDTO;
import com.HappyScrolls.entity.Comment;
import com.HappyScrolls.entity.Member;
import com.HappyScrolls.service.CommentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("comment")
public class CommentController {


    @Autowired
    private CommentService commentService;


    @ApiOperation(value = "댓글 생성")
    @PostMapping("")
    public ResponseEntity createParentComment(@AuthenticationPrincipal Member member, @RequestBody CommentDTO.ParentRequest request) {
        Comment response = commentService.commentParentCreate(member, request);
        return new ResponseEntity(toParentResponseDto(response), HttpStatus.CREATED);
    }

    @ApiOperation(value = "대댓글 생성")
    @PostMapping("/child")
    public ResponseEntity<CommentDTO.ChildResponse> createChildComment(@AuthenticationPrincipal Member member, @RequestBody CommentDTO.ChildRequest request) {
        Comment response = commentService.commentChildCreate(member, request);
        return new ResponseEntity(toChildResponseDto(response), HttpStatus.CREATED);
    }

    @ApiOperation(value = "게시글 id로 댓글 조회")
    @GetMapping("")
    public ResponseEntity<CommentDTO.Response> retrieveParentComment(@RequestParam Long id) {
        List<Comment> response = commentService.commentRetrieve(id);
        return new ResponseEntity(toCommentResponseDtoList(response), HttpStatus.ACCEPTED);
    }



    @ApiOperation(value = "댓글 수정")
    @PutMapping("")
    public ResponseEntity editComment(@AuthenticationPrincipal Member member, @RequestBody CommentDTO.Edit request) {
        Comment response = commentService.commentEdit(member, request);
        return new ResponseEntity(toParentResponseDto(response), HttpStatus.CREATED);
    }

    @ApiOperation(value = "댓글 수정")
    @DeleteMapping("")
    public ResponseEntity deleteComment(@AuthenticationPrincipal Member member, @RequestParam Long id) {
        commentService.commentDelete(member, id);
        return new ResponseEntity( HttpStatus.ACCEPTED);
    }

    public static CommentDTO.ParentResponse toParentResponseDto(Comment comment) {
        return CommentDTO.ParentResponse
                .builder()
                .id(comment.getId())
                .body(comment.getBody())
                .build();
    }

    public static CommentDTO.ChildResponse toChildResponseDto(Comment comment) {
        return CommentDTO.ChildResponse
                .builder()
                .id(comment.getId())
                .parentId(comment.getParentId())
                .body(comment.getBody())
                .build();
    }

    public static List<CommentDTO.Response> toCommentResponseDtoList(List<Comment> comments) {
        return comments.stream()
                .map(comment -> CommentDTO.Response
                .builder()
                .id(comment.getId())
                .body(comment.getBody())
                .isParent(comment.getIsParent())
                .parentId(comment.getParentId())
                .build())
                .collect(Collectors.toList());
    }

}
