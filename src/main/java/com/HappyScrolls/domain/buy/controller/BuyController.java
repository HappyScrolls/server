package com.HappyScrolls.domain.buy.controller;

import com.HappyScrolls.domain.buy.dto.BuyDTO;
import com.HappyScrolls.domain.member.entity.Member;
import com.HappyScrolls.domain.buy.service.BuyService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<List<Long>> createBuy(@AuthenticationPrincipal Member member, @RequestBody BuyDTO.RequestCart request) {
        System.out.println(member);
        List<Long> response = buyService.buyCreate(member,request);

        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "사용자 구매 조회")
    @GetMapping("/user")
    public ResponseEntity<List<BuyDTO.Response>> retrieveUserBuy(@AuthenticationPrincipal Member member) {

        return ResponseEntity.ok(buyService.buyRetrieveUser(member));
    }


}
