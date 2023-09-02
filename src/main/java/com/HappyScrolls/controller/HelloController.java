package com.HappyScrolls.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.boot.web.servlet.context.ServletWebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class HelloController  {

    @Value("${server.port}")
    private String url;
    @GetMapping
    public Integer hello(HttpServletRequest request) {
        System.out.println( request.getServerPort());
        System.out.println(url);
        return request.getServerPort();
    }
}
