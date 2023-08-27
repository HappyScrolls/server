package com.HappyScrolls.config;


import com.HappyScrolls.entity.Member;
import com.HappyScrolls.repository.MemberRepository;
import com.HappyScrolls.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
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
public class JwtRequestFilter extends GenericFilterBean {

    @Autowired
    private  JwtTokenUtil jwtTokenUtil;
    @Autowired
    private final MemberService memberService;

//    public JwtRequestFilter() {
//        this.jwtTokenUtil = new JwtTokenUtil();
//        this.memberService = new MemberService();
//    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {


        String token = ((HttpServletRequest)request).getHeader("Authorization").split(" ")[1];

        if (token != null) {

            String email = jwtTokenUtil.getUid(token);


            Member member = memberService.memberFind(email);


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