package com.HappyScrolls.service;

import com.HappyScrolls.entity.BuyEvent;
import com.HappyScrolls.entity.CommentEvent;
import com.HappyScrolls.entity.Member;
import com.HappyScrolls.exception.UserNotFoundException;
import com.HappyScrolls.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;
    public Member memberFind(String email) {
        return memberRepository.findByEmail(email).orElseThrow(()-> new UserNotFoundException(String.format("user[%s] 유저를  찾을 수 없습니다", email)));
    }

    public void decreasePoint(Member member, Integer requirePoints) {
        member.decreasePoint(requirePoints);
        memberRepository.save(member);
    }

    @EventListener
    public void test(BuyEvent event) {
        Member member = event.getMember();
        Integer requirePoints = event.getPrice();
        member.decreasePoint(requirePoints);
        memberRepository.save(member);
    }
    @EventListener
    public void commentEvent(CommentEvent event) {
        Member member = memberFind(event.getEmail());
        member.increasePoint(77);
        memberRepository.save(member);
    }


}
