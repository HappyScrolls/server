package com.HappyScrolls.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CommentEvent {

    private Comment parent;
    private Comment child;


}
