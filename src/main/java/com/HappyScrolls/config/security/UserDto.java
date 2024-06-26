package com.HappyScrolls.config.security;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserDto {
    private String email;
    private String nickname;

    @Builder
    public UserDto(String email, String nickname) {
        this.email = email;
        this.nickname = nickname;
    }
}