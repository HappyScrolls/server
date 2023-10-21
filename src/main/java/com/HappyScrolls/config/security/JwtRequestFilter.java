package com.HappyScrolls.config.security;


import com.HappyScrolls.entity.Member;
import com.HappyScrolls.exception.UserNotFoundException;
import com.HappyScrolls.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@RequiredArgsConstructor
public class JwtRequestFilter  extends OncePerRequestFilter {

    private  final JwtTokenUtil jwtTokenUtil;
    private final MemberService memberService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token;
        try {
            token = ((HttpServletRequest) request).getHeader("Authorization").split(" ")[1];
            System.out.println(token);
        } catch (Exception e) {
            throw new UserNotFoundException("user not found");
        }

        if (token != null) {

            String email = jwtTokenUtil.getUid(token);

            System.out.println(email);
            Member member = memberService.memberFind(email);
            System.out.println(member);

            Authentication auth = getAuthentication(member);
            System.out.println("@#"+auth);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }



    public Authentication getAuthentication(Member member) {
        return new UsernamePasswordAuthenticationToken(member, "",
                Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
    }
}