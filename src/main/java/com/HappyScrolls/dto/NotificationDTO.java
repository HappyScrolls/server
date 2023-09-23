package com.HappyScrolls.dto;
import com.HappyScrolls.entity.Notification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

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



    }
}
