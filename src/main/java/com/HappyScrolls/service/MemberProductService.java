package com.HappyScrolls.service;


import com.HappyScrolls.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberProductService {

    @Autowired
    private MemberRepository memberRepository;
}
