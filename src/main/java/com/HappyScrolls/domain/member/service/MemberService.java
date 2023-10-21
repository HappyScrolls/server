package com.HappyScrolls.domain.member.service;

import com.HappyScrolls.domain.member.adaptor.MemberAdaptor;
import com.HappyScrolls.domain.event.BuyEvent;
import com.HappyScrolls.domain.event.CommentEvent;
import com.HappyScrolls.domain.member.entity.Member;
import com.HappyScrolls.domain.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberAdaptor memberAdaptor;
    public Member memberFind(String email) {
        return memberAdaptor.memberFind(email);
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
        Member member = event.getChild().getMember();
        member.increasePoint(77);
        memberRepository.save(member);
    }


}
