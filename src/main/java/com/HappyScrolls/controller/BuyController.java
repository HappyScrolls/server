package com.HappyScrolls.controller;

import com.HappyScrolls.dto.BuyDTO;
import com.HappyScrolls.dto.CartDTO;
import com.HappyScrolls.entity.Member;
import com.HappyScrolls.service.BuyService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("buy")
public class BuyController {

    @Autowired
    private BuyService buyService;


    @ApiOperation(value = "구매 생성")
    @PostMapping("")
    public ResponseEntity createBuy(@AuthenticationPrincipal Member member, @RequestBody BuyDTO.RequestCart request) {

        List<BuyDTO.Response> response = buyService.buyCreate(member,request);

        return new ResponseEntity(response, HttpStatus.CREATED);
    }

    @ApiOperation(value = "사용자 구매 조회")
    @GetMapping("/user")
    public ResponseEntity<List<BuyDTO.Response>> retrieveUserBuy(@AuthenticationPrincipal Member member) {

        List<BuyDTO.Response> response = buyService.buyRetrieveUser(member);

        return new ResponseEntity<List<BuyDTO.Response>>(response, HttpStatus.CREATED);
    }


}
