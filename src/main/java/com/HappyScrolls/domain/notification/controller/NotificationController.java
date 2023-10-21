package com.HappyScrolls.domain.notification.controller;


import com.HappyScrolls.domain.notification.dto.NotificationDTO;
import com.HappyScrolls.domain.member.entity.Member;
import com.HappyScrolls.domain.notification.service.NotificationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;


    @ApiOperation(value = "유저의 알림 조회")
    @GetMapping("")
    public ResponseEntity<List<NotificationDTO.Response>> retrieveUserNotification(@AuthenticationPrincipal Member member) {
        return  ResponseEntity.ok(notificationService.userNotificationRetrieve(member));
    }


}
