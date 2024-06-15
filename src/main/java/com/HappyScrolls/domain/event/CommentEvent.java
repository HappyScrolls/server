package com.HappyScrolls.domain.event;

import com.HappyScrolls.domain.comment.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentEvent {

    private Comment parent;
    private Comment child;


}
