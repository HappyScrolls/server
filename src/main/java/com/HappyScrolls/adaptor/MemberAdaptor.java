package com.HappyScrolls.adaptor;

import com.HappyScrolls.entity.Member;
import com.HappyScrolls.exception.UserNotFoundException;
import com.HappyScrolls.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Adaptor
public class MemberAdaptor {


    @Autowired
    private MemberRepository memberRepository;
    public Member memberFind(String email) {
        return memberRepository.findByEmail(email).orElseThrow(()-> new UserNotFoundException(String.format("user[%s] 유저를  찾을 수 없습니다", email)));
    }

}
