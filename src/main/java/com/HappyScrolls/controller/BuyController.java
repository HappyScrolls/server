package com.HappyScrolls.controller;

import com.HappyScrolls.service.BuyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("buy")
public class BuyController {

    @Autowired
    private BuyService buyService;
}
