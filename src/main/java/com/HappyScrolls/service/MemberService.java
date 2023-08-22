package com.HappyScrolls.service;

import com.HappyScrolls.entity.Member;
import com.HappyScrolls.exception.UserNotFoundException;
import com.HappyScrolls.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;
    public Member memberFind(String email) {
        return memberRepository.findByEmail(email).orElseThrow(()-> new UserNotFoundException(String.format("user[%s] 유저를  찾을 수 없습니다", email)));
    }
}
