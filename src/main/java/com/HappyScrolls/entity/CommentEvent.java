package com.HappyScrolls.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CommentEvent {

    private String category;
    private String memberEmail;

    private String email;

    private Long refId;

    private String msg;

}
