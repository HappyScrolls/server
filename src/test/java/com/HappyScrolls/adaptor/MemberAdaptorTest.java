package com.HappyScrolls.adaptor;

import com.HappyScrolls.entity.Member;
import com.HappyScrolls.exception.UserNotFoundException;
import com.HappyScrolls.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class MemberAdaptorTest {

    @InjectMocks
    private MemberAdaptor memberAdaptor;

    @Mock
    private MemberRepository memberRepository;


    @Test
    @DisplayName("이메일로 멤버를 찾을 때, 멤버가 존재하는 경우")
    public void 멤버_찾기_성공() {

        Member member = Member.builder().email("test@email.com").build();

        when(memberRepository.findByEmail(anyString())).thenReturn(Optional.of(member));
        Member foundMember = memberAdaptor.memberFind("test@email.com");

        assertEquals("test@email.com", foundMember.getEmail());
    }

    @Test
    @DisplayName("이메일로 멤버를 찾을 때, 멤버가 존재하지 않는 경우")
    public void 멤버_찾기_실패() {
        when(memberRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> memberAdaptor.memberFind("test@email.com"));
    }
}
