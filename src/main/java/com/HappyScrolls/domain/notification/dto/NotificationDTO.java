package com.HappyScrolls.domain.notification.dto;
import com.HappyScrolls.domain.notification.entity.Notification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

public class NotificationDTO {

    @Builder
    @Getter
    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class Request{

        private String category;
        private String memberEmail;

        private Long refId;

        private String msg;

        public Notification toEntity() {
            return Notification.builder()
                    .category(this.category)
                    .refId(this.refId)
                    .msg(this.msg)
                    .build();
        }

    }

    @Builder
    @Getter
    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long id;

        private String category;

        private Long refId;

        private String msg;


        public static List<Response> toResponseDtoList(List<Notification> notifications) {
            return notifications.stream()
                    .map(notification -> NotificationDTO.Response.builder()
                            .id(notification.getId())
                            .category(notification.getCategory())
                            .msg(notification.getMsg())
                            .refId(notification.getRefId())
                            .build())
                    .collect(Collectors.toList());
        }
    }
}
