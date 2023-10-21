package com.HappyScrolls.domain.article.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Sticker {

    NEWHIT("NEW_HIT", "새 인기글"),
    OLDHIT("OLD_HIT", "오래된 인기글"),
    NONE("NONE", "NONE");

    private final String key;
    private final String title;
}
