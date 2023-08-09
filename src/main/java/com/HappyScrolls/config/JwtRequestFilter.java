package com.HappyScrolls.config;


import com.HappyScrolls.entity.Member;
import com.HappyScrolls.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
@Component
@RequiredArgsConstructor
@Service
public class JwtRequestFilter extends GenericFilterBean {
    private final JwtTokenUtil jwtTokenUtil;
    @Autowired
    private MemberRepository memberRepository;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println(((HttpServletRequest)request).getRequestURI());
        if (((HttpServletRequest) request).getRequestURI().contains("/h2-console")) {
            chain.doFilter(request, response);
            return;
        }
        String token = ((HttpServletRequest)request).getHeader("Authorization").split(" ")[1];
        System.out.println(token);
        System.out.println("filter1");
//        if (token != null && jwtTokenUtil.verifyToken(token)) {
        if (token != null) {
            System.out.println("filter1start");
            String email = jwtTokenUtil.getUid(token);
            System.out.println("verified usre" + email);

            Member member = memberRepository.findByEmail(email).get();
            // DB연동을 안했으니 이메일 정보로 유저를 만들어주겠습니다
            UserDto userDto = UserDto.builder()
                    .email(email)
                    .nickname("이름이에용")
                    .build();
            Authentication auth = getAuthentication(member);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        chain.doFilter(request, response);
    }

    public Authentication getAuthentication(Member member) {
        return new UsernamePasswordAuthenticationToken(member, "",
                Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
    }
}