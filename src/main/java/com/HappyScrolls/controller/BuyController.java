package com.HappyScrolls.controller;

import com.HappyScrolls.dto.BuyDTO;
import com.HappyScrolls.dto.CartDTO;
import com.HappyScrolls.entity.Buy;
import com.HappyScrolls.entity.Member;
import com.HappyScrolls.service.BuyService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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

        List<Buy> response = buyService.buyRetrieveUser(member);

        return ResponseEntity.ok(toResponseDtoList(response));
    }

    public static List<BuyDTO.Response> toResponseDtoList(List<Buy> buyList) {
        return buyList.stream()
                .map(buy -> BuyDTO.Response
                        .builder()
                        .id(buy.getId())
                        .productId(buy.getProduct().getId())
                        .createTime(buy.getCreateDate())
                        .build())
                .collect(Collectors.toList());

    }

}
