package com.HappyScrolls.service;

import com.HappyScrolls.entity.Cart;
import com.HappyScrolls.entity.Member;
import com.HappyScrolls.entity.Product;
import com.HappyScrolls.exception.UserNotFoundException;
import com.HappyScrolls.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

class MemberServiceTest {

    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;


    @Test
    void 멤버_조회_성공() {

        String testemail="chs98412@naver.com";
        Member member = Member.builder().id(1l).email(testemail).nickname("hyuksoon").thumbnail("img").build();


        when(memberRepository.findByEmail(any())).thenReturn(Optional.of(member));

        Member response = memberService.memberFind(testemail);

        verify(memberRepository).findByEmail(testemail);
        assertThat(response).isEqualTo(member);
    }


    @Test
    void 멤버_조회_실패() {
        String testemail="chs98412@naver.com";
        when(memberRepository.findByEmail(any())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> memberService.memberFind(testemail));


        verify(memberRepository).findByEmail(testemail);
    }

}