package com.HappyScrolls.controller;


import com.HappyScrolls.service.MemberProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("memberproduct")
public class MemberProductController {

    @Autowired
    private MemberProductService memberProductService;

}